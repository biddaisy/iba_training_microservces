package eu.ibagroup.bot.command;

public enum Command {
    START(),
    HELP,
    EVENTS(false),
    REGISTRATIONS(false),
    UNREGISTER(false),
    REGISTER(false),
    AUTHENTICATE,
    ABOUT,
    STATUS,
    CANCEL,
    EXIT;

    private boolean anonymous = true;

    Command() {}

    Command(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

}

