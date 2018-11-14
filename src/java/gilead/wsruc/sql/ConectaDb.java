/** 
    Compañia            : Gilead Consulting S.A.C.
    Sistema             : GC-Business
    Módulo              : Utilitarios
    Nombre              : ConectaDb.java
    Versión             : 1.0
    Fecha Creación      : 21-08-2018
    Autor Creación      : Pablo Jimenez Aguado
    Uso                 : Clase para obtener una conexion a la BD
*/
package gilead.wsruc.sql;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConectaDb {
    private String ds = "java:comp/env/WSRUC";

    public Connection getConnection() {
        Connection cn = null;

        try {
            Context c = new InitialContext();
            DataSource dataSource = (DataSource) c.lookup(ds);
            cn = dataSource.getConnection();

        } catch (NamingException ex) {
        } catch (SQLException ex) {
        }

        return cn;
    }
}
