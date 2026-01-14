package cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.exceptions;

public class ResourceExistsException extends RuntimeException {
    public ResourceExistsException(String message) {
        super(message);
    }
}
