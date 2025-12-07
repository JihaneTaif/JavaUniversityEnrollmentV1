// SecureChatClient.java
package chat;

import security.CryptoUtils;

import javax.crypto.SecretKey;
import java.io.*;
import java.net.Socket;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;

public class SecureChatClient {
    private String host;
    private int port;
    private String nickname;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private SecretKey aesKey;

    public SecureChatClient(String host, int port, String nickname) {
        this.host = host;
        this.port = port;
        this.nickname = nickname;
    }

    public void start() throws Exception {
        socket = new Socket(host, port);
        in  = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

        // 1) Read server RSA public key
        String line = in.readLine();
        if (line == null || !line.startsWith("PUBKEY ")) throw new IllegalStateException("No pubkey");
        String base64Pub = line.substring(7);
        PublicKey serverPub = decodePublicKey(base64Pub);

        // 2) Generate AES key and send to server encrypted with RSA
        aesKey = CryptoUtils.generateAESKey(256);
        String encKey = CryptoUtils.rsaEncrypt(aesKey.getEncoded(), serverPub);

        // 3) Send nickname then encrypted AES key
        out.println(nickname);
        out.println("KEY " + encKey);

        // 4) Start threads: one for reading, one for writing
        Thread reader = new Thread(this::readLoop);
        reader.setDaemon(true);
        reader.start();

        writeLoop(); // runs on main thread
    }

    private void readLoop() {
        try {
            String line;
            while ((line = in.readLine()) != null) {
                // decrypt AES-GCM message and print
                String msg = CryptoUtils.aesDecrypt(line, aesKey);
                System.out.println(msg);
            }
        } catch (Exception e) {
            System.out.println("Disconnected.");
        }
    }

    private void writeLoop() {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                String text = scanner.nextLine();
                if ("quit".equalsIgnoreCase(text)) {
                    out.println("QUIT");
                    break;
                }
                // encrypt "nickname:text"
                byte[] iv = CryptoUtils.randomIV(12);
                String payload = nickname + ":" + text;
                String enc = CryptoUtils.aesEncrypt(payload, aesKey, iv);
                out.println(enc);
            }
        } catch (Exception e) {
            // ignore
        } finally {
            try { socket.close(); } catch (IOException ignored) {}
        }
    }

    private static PublicKey decodePublicKey(String base64) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(base64);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }

    public static void main(String[] args) throws Exception {
        // Example usage: new SecureChatClient("127.0.0.1", 9000, "jihane").start();
        if (args.length < 3) {
            System.out.println("Usage: SecureChatClient <host> <port> <nickname>");
            return;
        }
        new SecureChatClient(args[0], Integer.parseInt(args[1]), args[2]).start();
    }
}
