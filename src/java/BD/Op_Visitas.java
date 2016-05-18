package BD;

import POJOS.Usuarios;
import POJOS.Visitas;
import es.cartera.HibernateUtil;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
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
