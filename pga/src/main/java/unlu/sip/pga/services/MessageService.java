package unlu.sip.pga.services;

import org.springframework.stereotype.Service;

import unlu.sip.pga.models.Message;

@Service
public class MessageService {

    public Message getPublicMessage() {
        final var text = "Mensaje publico.";

        return Message.from(text);
    }

    public Message getProtectedMessage() {
        final var text = "Mensaje protegido.";

        return Message.from(text);
    }

    public Message getAdminMessage() {
        final var text = "Mensaje administrador.";

        return Message.from(text);
    }
}
