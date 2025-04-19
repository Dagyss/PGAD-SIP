package unlu.sip.pga.services;

import unlu.sip.pga.models.Message;


public interface MessageService {

    Message getPublicMessage();

    Message getProtectedMessage();

    Message getAdminMessage();
}