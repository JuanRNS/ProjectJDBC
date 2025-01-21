package db;

import java.io.Serial;

public class DbIntegrityEx extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public DbIntegrityEx(String message) {
        super(message);
    }
}
