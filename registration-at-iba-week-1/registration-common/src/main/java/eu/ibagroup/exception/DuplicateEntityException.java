package eu.ibagroup.exception;

public class DuplicateEntityException extends Exception {

    public DuplicateEntityException(String duplicateId) {
        super("Duplicate entity id=" + duplicateId);
    }

    public static <T> T throwDuplicateEntityException(String duplicateId) throws DuplicateEntityException {
        throw new DuplicateEntityException(duplicateId);
    }
}
