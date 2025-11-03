package logic;

public class Protocol {
    public static final String SERVER = "localhost";
    public static final int PORT = 1234;



    // ===================== ADMINISTRADOR =====================
    public static final int ADMINISTRADOR_CREATE = 301;
    public static final int ADMINISTRADOR_READ = 302;
    public static final int ADMINISTRADOR_UPDATE = 303;
    public static final int ADMINISTRADOR_DELETE = 304;
    public static final int ADMINISTRADOR_SEARCH = 305;

    // ===================== DEPARTAMENTO =====================
    public static final int DEPARTAMENTO_CREATE = 401;
    public static final int DEPARTAMENTO_READ = 402;
    public static final int DEPARTAMENTO_UPDATE = 403;
    public static final int DEPARTAMENTO_DELETE = 404;
    public static final int DEPARTAMENTO_SEARCH = 405;

    // ===================== FARMACEUTICO =====================
    public static final int FARMACEUTICO_CREATE = 501;
    public static final int FARMACEUTICO_READ = 502;
    public static final int FARMACEUTICO_UPDATE = 503;
    public static final int FARMACEUTICO_DELETE = 504;
    public static final int FARMACEUTICO_SEARCH = 505;
    public static final int FARMACEUTICO_SEARCH_BY_NAME = 506;

    // ===================== MEDICAMENTO =====================
    public static final int MEDICAMENTO_CREATE = 601;
    public static final int MEDICAMENTO_READ = 602;
    public static final int MEDICAMENTO_UPDATE = 603;
    public static final int MEDICAMENTO_DELETE = 604;
    public static final int MEDICAMENTO_SEARCH = 605;
    public static final int MEDICAMENTO_SEARCH_BY_CODIGO = 606;
    public static final int MEDICAMENTO_SEARCH_BY_NAME = 607;

    // ===================== MEDICAMENTO DETALLE =====================
    public static final int MEDICAMENTO_DETALLE_CREATE = 701;
    public static final int MEDICAMENTO_DETALLE_READ = 702;
    public static final int MEDICAMENTO_DETALLE_UPDATE = 703;
    public static final int MEDICAMENTO_DETALLE_DELETE = 704;
    public static final int MEDICAMENTO_DETALLE_SEARCH = 705;

    // ===================== MEDICO =====================
    public static final int MEDICO_CREATE = 801;
    public static final int MEDICO_READ = 802;
    public static final int MEDICO_UPDATE = 803;
    public static final int MEDICO_DELETE = 804;
    public static final int MEDICO_SEARCH = 805;

    // ===================== MENSAJE =====================
    public static final int MENSAJE_CREATE = 901;
    public static final int MENSAJE_READ = 902;
    public static final int MENSAJE_UPDATE = 903;
    public static final int MENSAJE_DELETE = 904;
    public static final int MENSAJE_SEARCH = 905;

    // ===================== PACIENTE =====================
    public static final int PACIENTE_CREATE = 3001;
    public static final int PACIENTE_READ = 3002;
    public static final int PACIENTE_UPDATE = 3003;
    public static final int PACIENTE_DELETE = 3004;
    public static final int PACIENTE_SEARCH = 3005;
    public static final int PACIENTE_SEARCH_BY_ID =  3006;
    public static final int PACIENTE_SEARCH_BY_NAME =  3007;


    // ===================== RECETA =====================
    public static final int RECETA_CREATE = 1101;
    public static final int RECETA_READ = 1102;
    public static final int RECETA_UPDATE = 1103;
    public static final int RECETA_DELETE = 1104;
    public static final int RECETA_SEARCH = 1105;
    public static final int RECETA_SEARCH_BY_PACIENTE = 1106;
    public static final int RECETA_AVANZAR_ESTADO = 1107;
    public static final int RECETA_GENERAR_DETALLES = 1108;
    public static final int RECETA_SEARCH_BY_ID = 1109;
    public static final int RECETA_SEARCH_LIST_BY_ID = 1110;
    public static final int RECETA_REMOVE_MEDICAMENTO = 1111;
    public static final int RECETA_MODIFICAR_DETALLE = 1112;

    // ===================== USUARIO =====================
    public static final int USUARIO_CREATE = 1201;
    public static final int USUARIO_READ = 1202;
    public static final int USUARIO_UPDATE = 1203;
    public static final int USUARIO_DELETE = 1204;
    public static final int USUARIO_SEARCH = 1205;
    public static final int USUARIO_FIND_BY_ID = 1206;
    public static final int USUARIO_LOGIN = 1207;

    // ===================== ERRORES Y CONEXIÓN =====================
    public static final int ERROR_NO_ERROR = 0;
    public static final int ERROR_ERROR = 1;

    public static final int DISCONNECT = 99;
    public static final int SYNC = 10;
    public static final int ASYNC = 11;
    public static final int DELIVER_MESSAGE = 13;

    public static final int USUARIOS_ACTIVOS = 2001;
    public static final int USUARIO_ENVIAR_MENSAJE = 2002;
    public static final int USUARIO_RECIBIR_MENSAJE = 2003;
}