package eu.ibagroup.common.service;

import eu.ibagroup.common.mongo.SessionRepository;
import eu.ibagroup.common.mongo.collection.Session;
import eu.ibagroup.common.mongo.collection.State;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    public boolean isChatNew(long chatId) {
        return sessionRepository.findAllByChatId(chatId) == null;
    }

    public void initChat(long chatId) {
        sessionRepository.save(new Session(chatId));
    }

    public void setBotState(long chatId, @NonNull State state) {
        Session session = sessionRepository.findAllByChatId(chatId);
        session.setState(state);
        sessionRepository.save(session);
    }

    public State getBotState(Long chatId) {
        Session session = sessionRepository.findAllByChatId(chatId);
        if (session == null) {
            return null;
        }
        return session.getState();
    }

    public void setName(Long chatId, String name) {
        Session session = sessionRepository.findAllByChatId(chatId);
        session.setName(name);
        sessionRepository.save(session);
    }

    public void setEmail(Long chatId, String email) {
        Session session = sessionRepository.findAllByChatId(chatId);
        session.setEmail(email);
        sessionRepository.save(session);
    }

    public void confirmUser(Long chatId) {
        Session session = sessionRepository.findAllByChatId(chatId);
        session.setConfirmed(true);
        sessionRepository.save(session);
    }

    public long count() {
        return sessionRepository.count();
    }

    public boolean isUserConfirmed(Long chatId) {
        Session session = sessionRepository.findAllByChatId(chatId);
        return session.isConfirmed();
    }

    public Session getSession(Long chatId) {
        return sessionRepository.findAllByChatId(chatId);
    }

    public void deleteSession(Long chatId) {
        sessionRepository.deleteById(chatId);
    }

}
