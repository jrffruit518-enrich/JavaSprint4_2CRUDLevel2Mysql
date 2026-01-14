package cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
