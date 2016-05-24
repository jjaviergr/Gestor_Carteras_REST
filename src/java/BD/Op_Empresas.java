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

    public static void add(Empresas e) throws Exception {
        SessionFactory sfactory = HibernateUtil.getSessionFactory();
        Session session = sfactory.openSession();
        Transaction tx = null;
        try 
        {
            tx = session.beginTransaction();
            session.save(e);
            tx.commit();
        } 
        catch (HibernateException ex) 
        {
            if (tx != null) 
            {
                tx.rollback();
            }
            System.err.printf("Excepcion en Op_Empresa.add "+ex);
        } 
        finally 
        {
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

    public static Empresas find_by_name(String name)
    {
        Empresas resultado = null;

        SessionFactory sfactory = HibernateUtil.getSessionFactory();
        Session session = sfactory.openSession();
        try {
            //Query query = session.createQuery(hql);
            Criteria cs = session.createCriteria(Empresas.class);
            cs.add(Restrictions.eq("nombre", name));
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
    
    public static int update( int id,POJOS.Empresas e) 
    {
        Session session=null;
       try
       { 
        SessionFactory sfactory = HibernateUtil.getSessionFactory();
        session = sfactory.openSession();
        Transaction tx = session.beginTransaction();

        POJOS.Empresas old_user = (POJOS.Empresas) session.load(POJOS.Empresas.class, id);

        e.setId(old_user.getId());

        session.update(e); // modifica el objeto

        tx.commit(); //confirma transacción (sincronización con base de datos)

        
        return 1;
       }
       catch (Exception ex)
       {
           System.err.print("Excepcion en operacion update empresas en servicio "+ex);
           return 0;
       }
       finally
       {
           try
           {
               session.close();
           }
           catch(Exception ex)
           {
               System.err.print("Excepcion en operacion update empresas en servicio al cerrar sesion "+ex);
           }
       }
        
       
    }
}
