/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ivan;

import ivan.dao.ProductoDAO;
import ivan.dao.ProductoDAOImpl;
import ivan.model.Producto;

public class ProductoManager {
    public static void main(String[] args) {
        ProductoDAO product = new ProductoDAOImpl();

        product.insert(new Producto(210, "Pollo", 10.50));
        Producto p = product.read(210);
        System.out.println(p);
        product.update(new Producto(210, "Conejo", 20.0));
        Producto pa = product.read(210);
        System.out.println(pa);
        product.delete(210);
    }
    
}
