package BD;

import POJOS.Empresas;
import POJOS.Usuarios;
import POJOS.Visitas;
import es.cartera.HibernateUtil;
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
        List<Visitas> results = null;
        
        SessionFactory sfactory = HibernateUtil.getSessionFactory();
        Session session = sfactory.openSession();
        try {
            String hql = "from Visitas";
            Query query = session.createQuery(hql);
           
            resultsA = query.list();
          
//            Empresas e = (Empresas) ((SessionImplementor)session).getPersistenceContext().unproxy(resultsA.get(0).getEmpresas());
//            Object u = ((SessionImplementor)session).getPersistenceContext().unproxy(resultsA.get(0).getUsuarios());
            
            ArrayList datos=new ArrayList();
            datos.add(resultsA.get(0).getId());
            datos.add(resultsA.get(0).getEmpresas().toString());
            datos.add(resultsA.get(0).getUsuarios().toString());
            datos.add(resultsA.get(0).getMotivo());
            datos.add(resultsA.get(0).getResultado());
            datos.add(resultsA.get(0).getFecha());
            results=datos;

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

    public static List find(int user_id) {
        List results = null;
        SessionFactory sfactory = HibernateUtil.getSessionFactory();
        Session session = sfactory.openSession();
        try {
            //Query query = session.createQuery(hql);
            Criteria cs = session.createCriteria(Visitas.class);
            cs.add(Restrictions.eq("id", user_id));
            
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

}
