package ivan.dao;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import ivan.model.Producto;

public class ProductoDAOImpl implements ProductoDAO {

    static final String JDBC_DRIVER="com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/jdbc?useSSL=false";
    static final String DB_USR = "root";
    static final String DB_PWD = "";

    private void registerDriver() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException ex) {
            System.err.println("ERROR");
            ex.printStackTrace();
        }
    }

    @Override
     public void insert(Producto producto) {
        Connection conn = null;
        try {
            registerDriver();
            conn = DriverManager.getConnection(DB_URL, DB_USR, DB_PWD);
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("insert into producto values ("
                        + producto.getId() + ",'"
                        + producto.getNombre()+ "',"
                        + producto.getPrecio()+ ");");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void update(Producto producto) {
        Connection conn = null;
        try {
            registerDriver();
            conn = DriverManager.getConnection(DB_URL, DB_USR, DB_PWD);
            try (Statement stmt = conn.createStatement()) {
                String sql = "UPDATE producto SET nombre=?, precio=? WHERE id=?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, producto.getNombre());
                statement.setDouble(2, producto.getPrecio());
                statement.setInt(3, producto.getId());
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Se ha actualizado correctamente!");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void delete(Integer id) {
        Connection conn = null;
        try {
            registerDriver();
            conn = DriverManager.getConnection(DB_URL, DB_USR, DB_PWD);
            try (PreparedStatement ps = conn.prepareStatement(
                    "DELETE from producto where id = ?")) {
                ps.setInt(1, id);
                int rowsDeleted = ps.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Se ha borrado correctamente!");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Producto read(Integer id) {
        Connection conn = null;
        Producto producto = null;

        try {
            registerDriver();
            conn = DriverManager.getConnection(DB_URL, DB_USR, DB_PWD);
            try (PreparedStatement ps = conn.prepareStatement(
                    "select * from producto where id = ?")) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        producto = new Producto(id,
                                rs.getString("nombre"),
                                rs.getDouble("precio"));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return producto;
    }
}
