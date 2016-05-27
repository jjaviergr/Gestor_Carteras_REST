package BD;

import POJOS.Empresas;
import POJOS.Usuarios;
import POJOS.Visitas;
import es.cartera.HibernateUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author pc
 */
public class Op_Visitas {

    
    public static int get_minima_id()
    {
        int id_primera=-1;
        SessionFactory sfactory = HibernateUtil.getSessionFactory();
        Session session = sfactory.openSession();

        String hql1 = "SELECT MIN(V.id) FROM Visitas V";
        Query query1 = session.createQuery(hql1);
        if (query1.list().size() > 0) 
        {
            id_primera = (Integer) query1.list().get(0);
        }
        return id_primera;
    }
    
    public static Visitas get_primera_Visita() {
        Visitas una = null;
        SessionFactory sfactory = HibernateUtil.getSessionFactory();
        Session session = sfactory.openSession();

        String hql1 = "SELECT MIN(V.id) FROM Visitas V";
        Query query1 = session.createQuery(hql1);
        if (query1.list().size() > 0) 
        {
            int id_primera = (Integer) query1.list().get(0);

            String hql2 = "FROM Visitas v WHERE v.id = :Visitas_id";
            Query query2 = session.createQuery(hql2);
            query2.setParameter("Visitas_id", id_primera);

            una = (Visitas) query2.list().get(0);
        }
        return una;
    }

    @XmlElement
    public static List<Visitas> list() {
        List<Visitas> resultsA = null;
        List<Visitas> results = new ArrayList();

        SessionFactory sfactory = HibernateUtil.getSessionFactory();
        Session session = sfactory.openSession();
        try {
            String hql = "from Visitas";
            Query query = session.createQuery(hql);

            resultsA = query.list();

//            Empresas e = (Empresas) ((SessionImplementor)session).getPersistenceContext().unproxy(resultsA.get(0).getEmpresas());
//            Object u = ((SessionImplementor)session).getPersistenceContext().unproxy(resultsA.get(0).getUsuarios());
            List<String> lista_cadenas = new ArrayList();
            Visitas v;

            for (int i = 0; i < resultsA.size(); i++) {
                lista_cadenas.add(resultsA.get(i).toString());
            }

            ////////////////////////
            Integer id;
            Empresas e;
            Usuarios u;
            Date f;
            String resultado;
            String motivo;
            List<Visitas> lista_visitas = new ArrayList();
            for (int j = 0; j < lista_cadenas.size(); j++) {

                String[] elemento = lista_cadenas.get(j).split(",");//(1, 9);
                id = Integer.parseInt(elemento[0]);//1 de visita //9 son emprresa // 8 son de usuario // 2 son de visita //total 20
                List<String> lempresa = new ArrayList();
                for (int i = 1; i < 12; i++) {
                    lempresa.add(elemento[i]);
                }
                e = new Empresas(lempresa);
                List<String> lusuarios = new ArrayList();
                for (int i = 12; i < 21; i++) {
                    lusuarios.add(elemento[i]);
                }
                u = new Usuarios(lusuarios);

                SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    f = fecha.parse(elemento[20]);
                } catch (ParseException ex) {
                    System.err.print("exception con la fecha en contructor empresa " + ex);
                    f = null;
                }

                resultado = elemento[21];
                motivo = elemento[22];

                v = new Visitas(id, e, u, motivo, f, resultado);
                lista_visitas.add(v);
            }
            results = lista_visitas;
            //////////////////

            //System.err.print("pruebas :" + lista_visitas);
//Empresas empresas, Usuarios usuarios, Date fecha, String resultado, String motivo
//                v=new Visitas(id,e,u,motivo,f,resultado);
            //results.add(v);
        } catch (Exception e) {
            System.err.print(e.toString());
        } finally {
            session.close();
        }
        return (results);
    }

    /**
     * Devuelve el nº de visitas de la bd
     *
     * @return
     */
    public static int length() {
        String hql = "SELECT count(distinct v.id) FROM Visitas v";
        SessionFactory sfactory = HibernateUtil.getSessionFactory();
        Session session = sfactory.openSession();
        Query query = session.createQuery(hql);
        List results = query.list();
        return (results.size());
    }

    public static int add(POJOS.Visitas u) {
        SessionFactory sfactory = HibernateUtil.getSessionFactory();
        Session session = sfactory.openSession();
        Transaction tx = session.beginTransaction();
        Integer id=(Integer) session.save(u); //almacena el objeto en contexto de persistencia
        
        tx.commit(); //confirma transacción (sincronización con base de datos)
        session.close();
        return id;
    }

    public static List find(int user_id, Date fecha) {

        List results = null;
        SessionFactory sfactory = HibernateUtil.getSessionFactory();
        Session session = sfactory.openSession();
        try {
            //Query query = session.createQuery(hql);
            Criteria cs = session.createCriteria(Visitas.class);
            cs.add(Restrictions.eq("usuarios_id", user_id));
            cs.add(Restrictions.eq("fecha", fecha));
            results = cs.list();

        } catch (Exception e) {

        } finally {
            session.close();
        }
        return (results);
    }

    public static List find_by_nombre_empresa(String nombre_empresa) {
        List results = new ArrayList();
        results.add("POR IMPLEMENTAR");
//        Empresas em = Op_Empresas.find_by_name(nombre_empresa);
//        Integer id = em.getId();
//        
//        SessionFactory sfactory = HibernateUtil.getSessionFactory();
//        Session session = sfactory.openSession();
//       // Transaction tx = null;
//        try
//        {
//           // tx = session.beginTransaction();
//            String sql = "SELECT * FROM Visitas where Empresas like '"+em+"'";
//            SQLQuery query = session.createSQLQuery(sql);
//            query.addEntity(Visitas.class);
//            results = query.list();
//
//
//            //tx.commit();
//          }
//          catch (HibernateException e) 
//          {
//            // if (tx!=null) tx.rollback();
//             e.printStackTrace(); 
//          }
//          finally 
//          {
//             session.close(); 
//          }
////        SessionFactory sfactory = HibernateUtil.getSessionFactory();
////        Session session = sfactory.openSession();
////        try {
////
////            //Query query = session.createQuery(hql);
////            Criteria cs = session.createCriteria(Visitas.class);
////            cs.add(Restrictions.eq("empresas", em));
////            if (cs.list().size() > 0) {
////                List<String> lista = new ArrayList();
////                String resultado = cs.list().toString();
////                Visitas v=new Visitas(resultado);                
////                results=new ArrayList();
////                results.add(v);
////                
////            }
////
////            
////            //tx.commit();
////            return (results);
////
////        } catch (Exception e) {
////
////        } finally {
////            session.close();
////            return (results);
////        }
        return (results);
    }

    public static List find(int user_id, int empresa_id) {
        Visitas resultado = null;
        List results = null;
        SessionFactory sfactory = HibernateUtil.getSessionFactory();
        Session session = sfactory.openSession();
        try {
            //Query query = session.createQuery(hql);
            Criteria cs = session.createCriteria(Visitas.class);
            cs.add(Restrictions.eq("usuarios_id", user_id));
            cs.add(Restrictions.eq("empresas_id", empresa_id));
            results = cs.list();
            //tx.commit();

        } catch (Exception e) {

        } finally {
            session.close();
        }
        return (results);
    }

    public static void update(String id, POJOS.Visitas u) {
        SessionFactory sfactory = HibernateUtil.getSessionFactory();
        Session session = sfactory.openSession();
        Transaction tx = session.beginTransaction();

        POJOS.Visitas old_user = null;

        old_user = (POJOS.Visitas) session.load(POJOS.Visitas.class, id);

        u.setId(old_user.getId());

        session.update(u); // modifica el objeto

        tx.commit(); //confirma transacción (sincronización con base de datos)

        session.close();
    }

    public static void delete(String id) {
        SessionFactory sfactory = HibernateUtil.getSessionFactory();
        Session session = sfactory.openSession();
        Transaction tx = session.beginTransaction();

        POJOS.Visitas u = (POJOS.Visitas) session.load(POJOS.Visitas.class, id);
        session.delete(u); // elimina el objeto

        tx.commit();
        session.close();

    }

    public static Visitas find_by_id(String id) {

        List results = null;
        Visitas v = null;
        String cadena;
        SessionFactory sfactory = HibernateUtil.getSessionFactory();
        Session session = sfactory.openSession();
        try {
            //Query query = session.createQuery(hql);
            Criteria cs = session.createCriteria(Visitas.class);
            cs.add(Restrictions.eq("id", Integer.parseInt(id)));
            results = cs.list();
            cadena = results.get(0).toString();
            int longitud = cadena.split(",").length;
            v = new Visitas(cadena);
            // v=new Visitas(cadena.split(",")[0],cadena.split(",")[0],cadena.split(",")[0],cadena.split(",")[0],cadena.split(",")[0],cadena.split(",")[0],);

        } catch (Exception e) {

        } finally {
            session.close();
        }

        return (v);
    }

}
