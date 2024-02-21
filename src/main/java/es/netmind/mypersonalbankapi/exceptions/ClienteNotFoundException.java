package es.netmind.mypersonalbankapi.exceptions;


public class ClienteNotFoundException extends GlobalException {
    private static final long serialVersionUID = 1L;

    public ClienteNotFoundException(String message) {
        super(message);
    }

    public ClienteNotFoundException(Integer clientId) {
        super("Client with id: " + clientId + " not found");
    }
}
