package BD;

import POJOS.Empresas;
import POJOS.Usuarios;
import POJOS.Visitas;
import es.cartera.HibernateUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.SessionImplementor;

/**
 *
 * @author pc
 */
public class Op_Visitas {

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
            for (int j=0;j<lista_cadenas.size();j++) {

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
                try 
                {
                    f = fecha.parse(elemento[20]);
                } 
                catch (ParseException ex) 
                {
                    System.err.print("exception con la fecha en contructor empresa " + ex);
                    f=null;
                }
                

                resultado = elemento[21];
                motivo = elemento[22];

                v = new Visitas(id, e, u, motivo, f, resultado);
                lista_visitas.add(v);
            }
            results=lista_visitas;
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

    public static void add(POJOS.Visitas u) {
        SessionFactory sfactory = HibernateUtil.getSessionFactory();
        Session session = sfactory.openSession();
        Transaction tx = session.beginTransaction();

        session.save(u); //almacena el objeto en contexto de persistencia
        tx.commit(); //confirma transacción (sincronización con base de datos)
        session.close();
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

    public static List find_by_nombre_empresa(String  nombre_empresa) {
        List results = null;
         Empresas em=Op_Empresas.find_by_name(nombre_empresa);
         Integer id=em.getId();
        SessionFactory sfactory = HibernateUtil.getSessionFactory();
        Session session = sfactory.openSession();
        try {
           
            
            //Query query = session.createQuery(hql);
            
            Criteria cs = session.createCriteria(Visitas.class);
            cs.add(Restrictions.eq("empresas", em));
            
          

            results = cs.list();
            //tx.commit();

        } catch (Exception e) {

        } finally {
            session.close();
        }
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
        Visitas v=null;
        String cadena;
        SessionFactory sfactory = HibernateUtil.getSessionFactory();
        Session session = sfactory.openSession();
        try {
            //Query query = session.createQuery(hql);
            Criteria cs = session.createCriteria(Visitas.class);
            cs.add(Restrictions.eq("id", Integer.parseInt(id)));            
            results = cs.list();
            cadena=results.get(0).toString();
            int longitud=cadena.split(",").length;
            v=new Visitas(cadena);
           // v=new Visitas(cadena.split(",")[0],cadena.split(",")[0],cadena.split(",")[0],cadena.split(",")[0],cadena.split(",")[0],cadena.split(",")[0],);

        } catch (Exception e) {

        } finally {
            session.close();
        }
        
        return (v);
    }

}
