// ClientHandler.java
package chat;

import security.CryptoUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.Socket;
import java.security.PublicKey;

public class ClientHandler implements Runnable {
    private Socket socket;
    private SecureChatServer server;
    private BufferedReader in;
    private PrintWriter out;

    private String nickname;
    private SecretKey aesKey; // per-client AES key

    public ClientHandler(Socket socket, SecureChatServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            in  = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

            // Step 1: send server RSA public key (Base64)
            String pub = java.util.Base64.getEncoder().encodeToString(server.getServerPublicKey().getEncoded());
            out.println("PUBKEY " + pub);

            // Step 2: receive nickname
            nickname = in.readLine();
            if (nickname == null || nickname.trim().isEmpty()) {
                close();
                return;
            }

            // Step 3: receive AES key encrypted with server RSA public key
            String encKeyLine = in.readLine(); // "KEY <base64>"
            if (encKeyLine == null || !encKeyLine.startsWith("KEY ")) {
                close();
                return;
            }
            String base64EncKey = encKeyLine.substring(4);
            byte[] rawKey = server.decryptClientAESKey(base64EncKey);
            aesKey = new SecretKeySpec(rawKey, "AES");

            server.registerClient(nickname, this);

            // Step 4: main loop for encrypted messages
            String line;
            while ((line = in.readLine()) != null) {
                if (line.equals("QUIT")) {
                    break;
                }
                // line is base64 AES-GCM. Decrypt to plaintext "nickname:text"
                String plaintext = CryptoUtils.aesDecrypt(line, aesKey);
                Message msg = Message.parse(plaintext);
                // Re-broadcast to everyone
                server.broadcastUserMessage(msg.getNickname(), msg.getText());
            }
        } catch (Exception e) {
            // optional logging
        } finally {
            server.unregisterClient(nickname);
            close();
        }
    }

    // Server notice to this client (encrypt with this client's AES key)
    public void sendPlainServerNotice(String notice) {
        try {
            byte[] iv = CryptoUtils.randomIV(12);
            String enc = CryptoUtils.aesEncrypt("server:" + notice, aesKey, iv);
            out.println(enc);
        } catch (Exception e) {
            // ignore errors
        }
    }

    // Send user message to this client (encrypt with this client's AES key)
    public void sendUserMessage(String fromNick, String text) {
        try {
            byte[] iv = CryptoUtils.randomIV(12);
            String enc = CryptoUtils.aesEncrypt(fromNick + ":" + text, aesKey, iv);
            out.println(enc);
        } catch (Exception e) {
            // ignore
        }
    }

    private void close() {
        try { socket.close(); } catch (IOException ignored) {}
    }
}
