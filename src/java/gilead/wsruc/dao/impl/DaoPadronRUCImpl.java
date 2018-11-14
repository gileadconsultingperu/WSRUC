package gilead.wsruc.dao.impl;

import gilead.wsruc.model.BeanPadronRUC;
import gilead.wsruc.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DaoPadronRUCImpl {

    public List<BeanPadronRUC> listPadronRUC() {
        BeanPadronRUC padronRUC = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<BeanPadronRUC> listPadronRUC = null;

        if (cn != null) {
            try {
                String qry = "SELECT ruc, razon_social, estado_contribuyente, condicion_domicilio, ubigeo, tipo_via, nombre_via, codigo_zona, tipo_zona, numero, interior, lote, departamento, manzana, kilometro"
                        + " FROM public.padron_reducido_ruc ORDER BY razon_social";

                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listPadronRUC = new LinkedList<BeanPadronRUC>();

                while (rs.next()) {
                    padronRUC = new BeanPadronRUC();
                    padronRUC.setRuc(rs.getString("ruc"));
                    padronRUC.setRazon_social(rs.getString("razon_social"));
                    padronRUC.setEstado_contribuyente(rs.getString("estado_contribuyente"));
                    padronRUC.setCondicion_domicilio(rs.getString("condicion_domicilio"));
                    padronRUC.setUbigeo(rs.getString("ubigeo"));
                    padronRUC.setTipo_via(rs.getString("tipo_via"));
                    padronRUC.setNombre_via(rs.getString("nombre_via"));
                    padronRUC.setCodigo_zona(rs.getString("codigo_zona"));
                    padronRUC.setTipo_zona(rs.getString("tipo_zona"));
                    padronRUC.setNumero(rs.getString("numero"));
                    padronRUC.setInterior(rs.getString("interior"));
                    padronRUC.setLote(rs.getString("lote"));
                    padronRUC.setDepartamento(rs.getString("departamento"));
                    padronRUC.setManzana(rs.getString("manzana"));
                    padronRUC.setKilometro(rs.getString("kilometro"));

                    listPadronRUC.add(padronRUC);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                padronRUC = null;
            }
        }

        return listPadronRUC;
    }

    public BeanPadronRUC getPadronRUC(String ruc) {
        BeanPadronRUC padronRUC = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        if (cn != null) {
            try {
                String qry = "SELECT pa.ruc, pa.razon_social, pa.estado_contribuyente, pa.condicion_domicilio, pa.ubigeo, pa.tipo_via, pa.nombre_via, pa.codigo_zona,"
                        + " pa.tipo_zona, pa.numero, pa.interior, pa.lote, pa.departamento, pa.manzana, pa.kilometro, di.descripcion udistrito, pr.descripcion uprovincia,"
                        + " de.descripcion udepartamento"
                        + " FROM public.padron_reducido_ruc pa"
                        + " LEFT JOIN public.ubidistrito di on di.codigo_ubidistrito=pa.ubigeo"
                        + " LEFT JOIN public.ubiprovincia pr on pr.codigo_ubiprovincia=di.codigo_ubiprovincia"
                        + " LEFT JOIN public.ubidepartamento de on de.codigo_ubidepartamento=pr.codigo_ubidepartamento"
                        + " WHERE ruc = '" + ruc + "'";

                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                while (rs.next()) {
                    padronRUC = new BeanPadronRUC();
                    padronRUC.setRuc(rs.getString("ruc"));
                    padronRUC.setRazon_social(rs.getString("razon_social"));
                    padronRUC.setEstado_contribuyente(rs.getString("estado_contribuyente"));
                    padronRUC.setCondicion_domicilio(rs.getString("condicion_domicilio"));
                    padronRUC.setUbigeo(rs.getString("ubigeo"));
                    padronRUC.setTipo_via(rs.getString("tipo_via"));
                    padronRUC.setNombre_via(rs.getString("nombre_via"));
                    padronRUC.setCodigo_zona(rs.getString("codigo_zona"));
                    padronRUC.setTipo_zona(rs.getString("tipo_zona"));
                    padronRUC.setNumero(rs.getString("numero"));
                    padronRUC.setInterior(rs.getString("interior"));
                    padronRUC.setLote(rs.getString("lote"));
                    padronRUC.setDepartamento(rs.getString("departamento"));
                    padronRUC.setManzana(rs.getString("manzana"));
                    padronRUC.setKilometro(rs.getString("kilometro"));
                    String direccion = "";
                    if (rs.getString("tipo_via") != null) {
                        if (!rs.getString("tipo_via").equals("-") && !rs.getString("tipo_via").equals("----")) {
                            direccion = direccion + rs.getString("tipo_via") + " ";
                        }
                    }
                    if (rs.getString("nombre_via") != null) {
                        if (!rs.getString("nombre_via").equals("-") && !rs.getString("nombre_via").equals("----")) {
                            direccion = direccion + rs.getString("nombre_via") + " ";
                        }
                    }
                    if (rs.getString("numero") != null) {
                        if (!rs.getString("numero").equals("-") && !rs.getString("numero").equals("----")) {
                            direccion = direccion + "NRO. " + rs.getString("numero") + " ";
                        }
                    }
                    if (rs.getString("interior") != null) {
                        if (!rs.getString("interior").equals("-") && !rs.getString("interior").equals("----")) {
                            direccion = direccion + "INT. " + rs.getString("interior") + " ";
                        }
                    }
                    if (rs.getString("kilometro") != null) {
                        if (!rs.getString("kilometro").equals("-") && !rs.getString("kilometro").equals("----")) {
                            direccion = direccion + "KM. " + rs.getString("kilometro") + " ";
                        }
                    }
                    if (rs.getString("manzana") != null) {
                        if (!rs.getString("manzana").equals("-") && !rs.getString("manzana").equals("----")) {
                            direccion = direccion + "MZ. " + rs.getString("manzana") + " ";
                        }
                    }
                    if (rs.getString("lote") != null) {
                        if (!rs.getString("lote").equals("-") && !rs.getString("lote").equals("----")) {
                            direccion = direccion + "LT. " + rs.getString("lote") + " ";
                        }
                    }
                    if (rs.getString("departamento") != null) {
                        if (!rs.getString("departamento").equals("-") && !rs.getString("departamento").equals("----")) {
                            direccion = direccion + "DPTO. " + rs.getString("departamento") + " ";
                        }
                    }
                    if (rs.getString("codigo_zona") != null) {
                        if (!rs.getString("codigo_zona").equals("-") && !rs.getString("codigo_zona").equals("----")) {
                            direccion = direccion + rs.getString("codigo_zona") + " ";
                        }
                    }
                    if (rs.getString("tipo_zona") != null) {
                        if (!rs.getString("tipo_zona").equals("-") && !rs.getString("tipo_zona").equals("----")) {
                            direccion = direccion + rs.getString("tipo_zona") + " ";
                        }
                    }
                    padronRUC.setDireccion(direccion.trim());
                    padronRUC.setDireccioncompleta(direccion + rs.getString("udepartamento") + " - " + rs.getString("uprovincia") + " - " + rs.getString("udistrito"));
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                padronRUC = null;
            }
        }

        return padronRUC;
    }
}
