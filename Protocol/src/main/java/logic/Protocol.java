package logic;

public class Protocol {
    public static final String SERVER = "localhost";
    public static final int PORT = 1234;

    public static final int PRODUCTO_CREATE=101;
    public static final int PRODUCTO_READ=102;
    public static final int PRODUCTO_UPDATE=103;
    public static final int PRODUCTO_DELETE=104;
    public static final int PRODUCTO_SEARCH=105;
    public static final int CATEGORIA_SEARCH=205;
    public static final int ERROR_NO_ERROR=0;
    public static final int ERROR_ERROR=1;

    public static final int DISCONNECT=99;
}