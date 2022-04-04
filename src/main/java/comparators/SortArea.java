package comparators;

import java.util.Comparator;
import loja.Loja;

/**
 * Classe que implementa a interface Comparator do tipo Loja e que compara as áreas dos 2 elementos.
 */
public class SortArea implements Comparator<Loja>
{
    @Override
    public int compare(Loja o1, Loja o2)
    {
        return o1.getArea() - o2.getArea();
    }
}