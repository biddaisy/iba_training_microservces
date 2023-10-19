package eu.ibagroup.common.service;

import eu.ibagroup.common.mongo.SessionRepository;
import eu.ibagroup.common.mongo.collection.Session;
import eu.ibagroup.common.mongo.collection.State;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    public boolean isChatNew(Long chatId) {
        return sessionRepository.findAllByChatId(chatId) == null;
    }

    public void initChat(Long chatId) {
        sessionRepository.save(new Session(chatId));
    }

    public void setBotState(Long chatId, State state) {
        val session = sessionRepository.findAllByChatId(chatId);
        session.setState(state);
        sessionRepository.save(session);
    }

    public State getBotState(Long chatId) {
        val session = sessionRepository.findAllByChatId(chatId);
        return session == null ? null : session.getState();
    }

    public void setName(Long chatId, String name) {
        val session = sessionRepository.findAllByChatId(chatId);
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
