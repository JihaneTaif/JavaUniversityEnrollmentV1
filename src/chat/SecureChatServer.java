// SecureChatServer.java
package chat;

import security.CryptoUtils;

import javax.crypto.SecretKey;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SecureChatServer {
    private int port;
    private KeyPair serverKeyPair; // RSA keypair
    // Map of client nickname -> ClientHandler
    private Map<String, ClientHandler> clients = new ConcurrentHashMap<>();

    public static void main(String[] args) throws Exception {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 9000;
        new SecureChatServer(port).start();
    }


    public SecureChatServer(int port) throws Exception {
        this.port = port;
        this.serverKeyPair = CryptoUtils.generateRSAKeyPair(2048);
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server listening on port " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(socket, this);
                new Thread(handler).start();
            }
        }
    }

    // Send a plaintext server notice to everyone
    public void broadcastServerNotice(String notice) {
        for (ClientHandler ch : clients.values()) {
            ch.sendPlainServerNotice(notice);
        }
    }

    // Broadcast a user message: server receives plaintext from one handler,
    // then re-encrypts for each recipient using their AES key.
    public void broadcastUserMessage(String fromNick, String text) {
        for (ClientHandler ch : clients.values()) {
            ch.sendUserMessage(fromNick, text);
        }
    }

    public void registerClient(String nick, ClientHandler handler) {
        clients.put(nick, handler);
        broadcastServerNotice(nick + " joined the chat.");
    }

    public void unregisterClient(String nick) {
        clients.remove(nick);
        broadcastServerNotice(nick + " left the chat.");
    }

    public PublicKey getServerPublicKey() {
        return serverKeyPair.getPublic();
    }

    public byte[] decryptClientAESKey(String base64Cipher) throws Exception {
        return CryptoUtils.rsaDecrypt(base64Cipher, serverKeyPair.getPrivate());
    }
}
