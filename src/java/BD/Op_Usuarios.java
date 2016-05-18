package BD;

import POJOS.Usuarios;
import es.cartera.HibernateUtil;
import java.util.List;
import java.util.Set;
import javax.xml.bind.annotation.XmlElement;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author pc
 */
public class Op_Usuarios {

    public static int add(POJOS.Usuarios u) {
        try {
            SessionFactory sfactory = HibernateUtil.getSessionFactory();
            Session session = sfactory.openSession();
            Transaction tx = session.beginTransaction();

            session.save(u); //almacena el objeto en contexto de persistencia
            tx.commit(); //confirma transacción (sincronización con base de datos)
            session.close();
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    public static Usuarios find_by_id(int id) {
        Usuarios resultado = null;

        SessionFactory sfactory = HibernateUtil.getSessionFactory();
        Session session = sfactory.openSession();
        try {
            //Query query = session.createQuery(hql);
            Criteria cs = session.createCriteria(Usuarios.class);
            cs.add(Restrictions.eq("id", id));
            List results = cs.list();
            //tx.commit();
            if (!results.isEmpty()) {

                resultado = (Usuarios) results.get(0);
            }

        } catch (Exception e) {

        } finally {
            session.close();
        }
        return (resultado);
    }
    
    @XmlElement
    public static List<Usuarios> list() {
        List<Usuarios> results = null;

        SessionFactory sfactory = HibernateUtil.getSessionFactory();
        Session session = sfactory.openSession();
        try {
            String hql = "from Usuarios";
            Query query = session.createQuery(hql);

            results = query.list();

        } catch (Exception e) {
              System.err.print(e.toString());
        } finally {
            session.close();
        }
        return (results);
    }

    public static Usuarios find_by_login(String login) {
        Usuarios resultado = null;

        SessionFactory sfactory = HibernateUtil.getSessionFactory();
        Session session = sfactory.openSession();
        try {
            //Query query = session.createQuery(hql);
            Criteria cs = session.createCriteria(Usuarios.class);
            cs.add(Restrictions.eq("login", login));
            List results = cs.list();
            //tx.commit();
            if (!results.isEmpty()) {

                resultado = (Usuarios) results.get(0);
            }

        } catch (Exception e) {

        } finally {
            session.close();
        }
        return (resultado);
    }

    public static int update(int id, POJOS.Usuarios u) {
        try
        {
            SessionFactory sfactory = HibernateUtil.getSessionFactory();
            Session session = sfactory.openSession();
            Transaction tx = session.beginTransaction();

            POJOS.Usuarios old_user = null;

            old_user = (POJOS.Usuarios) session.load(POJOS.Usuarios.class, id);

            u.setId(old_user.getId());

            session.update(u); // modifica el objeto

            tx.commit(); //confirma transacción (sincronización con base de datos)

            session.close();
            return 1;
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    public static int delete(int id) {
        try
        {
            SessionFactory sfactory = HibernateUtil.getSessionFactory();
            Session session = sfactory.openSession();
            Transaction tx = session.beginTransaction();

            POJOS.Usuarios u = (POJOS.Usuarios) session.load(POJOS.Usuarios.class, id);
            session.delete(u); // elimina el objeto

            tx.commit();
            session.close();
            return 1;
        }
        catch (Exception e)
        {
            return 0;
        }

    }

    public static boolean validar(String login, String pass) {
        POJOS.Usuarios user = Op_Usuarios.find_by_login(login);
        if (user != null) {
            if (user.getPass().matches(pass)) {
                return true;
            }
        }
        return false;
    }

    public static String imprimir(Usuarios u) {
        String separador = ";";
        String cadena = u.getLogin() + separador;
        cadena += u.getNif() + separador;
        cadena += u.getNombre() + separador + "\n";
        return (cadena);

    }

    public static boolean esAdm(String login) {
        Usuarios usu = find_by_login(login);
        if (usu.isEsAdm()) {
            return true;
        } else {
            return false;
        }
    }

    public static Set extrae_visitas(String login) {
        Usuarios us = find_by_login(login);
        return (us.getVisitases());

    }

}
