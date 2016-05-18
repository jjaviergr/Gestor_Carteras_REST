package BD;

import POJOS.Empresas;
import es.cartera.HibernateUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;


// *
// * @author pc
// */
public class Op_Empresas {

    public static void add(Empresas e) {
        SessionFactory sfactory = HibernateUtil.getSessionFactory();
        Session session = sfactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(e);
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            ex.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static List list() {
        List resultado = null;

        SessionFactory sfactory = HibernateUtil.getSessionFactory();
        Session session = sfactory.openSession();
        try {
            String hql = "from Empresas";
            Query query = session.createQuery(hql);

            resultado = query.list();

        } catch (Exception e) {

        } finally {
            session.close();
        }
        return (resultado);
    }

    public static Empresas find(String cif) {
        Empresas resultado = null;

        SessionFactory sfactory = HibernateUtil.getSessionFactory();
        Session session = sfactory.openSession();
        try {
            //Query query = session.createQuery(hql);
            Criteria cs = session.createCriteria(Empresas.class);
            cs.add(Restrictions.eq("cif", cif));
            List results = cs.list();
            //tx.commit();
            if (!results.isEmpty()) {

                resultado = (Empresas) results.get(0);
            }

        } catch (Exception e) {

        } finally {
            session.close();
        }

        return resultado;
    }

    public static Empresas find(int id) {
        Empresas resultado = null;

        SessionFactory sfactory = HibernateUtil.getSessionFactory();
        Session session = sfactory.openSession();
        try {
            //Query query = session.createQuery(hql);
            Criteria cs = session.createCriteria(Empresas.class);
            cs.add(Restrictions.eq("id", id));
            List results = cs.list();
            //tx.commit();
            if (!results.isEmpty()) {

                resultado = (Empresas) results.get(0);
            }

        } catch (Exception e) {

        } finally {
            session.close();
        }

        return resultado;
    }

    public static void delete(int id) {
        SessionFactory sfactory = HibernateUtil.getSessionFactory();
        Session session = sfactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "DELETE FROM Empresas WHERE id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    public static void update( POJOS.Empresas e)
    {
           
        SessionFactory sfactory = HibernateUtil.getSessionFactory();
        Session session = sfactory.openSession();
        Transaction tx = session.beginTransaction();

        POJOS.Empresas old_Empresa = null;

        old_Empresa = (POJOS.Empresas) session.load(POJOS.Empresas.class, e.getId());

        e.setId(old_Empresa.getId());

        session.update(e); // modifica el objeto

        tx.commit(); //confirma transacción (sincronización con base de datos)

        session.close();
    
    }
}
