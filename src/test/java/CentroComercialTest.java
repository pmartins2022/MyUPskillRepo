import comparators.SortArea;
import comparators.SortId;
import comparators.SortType;
import loja.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


class CentroComercialTest
{
    @Test
    void testLojaQuiosqueRenda()
    {
        ComFuncionarios j = new LojaQuiosque("ola", 800, 800f, 10);

        float expected = 20f;
        float actual = j.calculaRenda();

        assertEquals(expected, actual);
    }

    @Test
    void testToString()
    {
        ComFuncionarios j = new LojaQuiosque("ola", 200, 45f, 10);
        ComFuncionarios k = new LojaRestauracao("ola", 200, 45f, 10, 25, 200f);
        Loja l = new AncoraPropria("ola", 200, 45f, 150f);

        System.out.println(j);
        System.out.println("---------------------------------------");
        System.out.println(k);
        System.out.println("---------------------------------------");
        System.out.println(l);
        System.out.println("---------------------------------------");
    }

    @Test
    void testLojaEquals()
    {
        LojaRestauracao x = new LojaRestauracao();

        x.setArea(50);
        x.setNome("aaa");
        x.setReceitas(500f);
        x.setCustoManutencao(25f);
        x.setNumMesas(10);
        x.setNumeroFuncionarios(10);

        LojaRestauracao k = new LojaRestauracao("aaa", 50, 500f, 10, 10, 25f);

        boolean expected = true;

        boolean actual = x.equals(k);

        assertTrue(expected == actual);
    }

    @Test
    void testCentroComercialReceitas()
    {
        CentroComercial x = new CentroComercial();

        x.setNome("oii");
        x.setMorada("R.NossaSenhora");

        x.adicionarLoja(new LojaRestauracao("aa", 50, 8000f, 10, 10, 10f));

        float expected = 1305.0f;

        assertEquals(expected, x.calcularTotalReceitas());
    }

    @Test
    void testCentroComercialRemove()
    {
        CentroComercial x = new CentroComercial();

        x.setNome("oii");
        x.setMorada("R.NossaSenhora");

        x.adicionarLoja(new LojaRestauracao("aa", 50, 8000f, 10, 10, 10f));

        boolean expected = true;

        assertTrue(expected == x.removerLoja(new LojaRestauracao("aa", 50, 8000f, 10, 10, 10f)));
    }

    @Test
    void testCentroComercialRemoveID()
    {
        CentroComercial x = new CentroComercial();

        x.setNome("oii");
        x.setMorada("R.NossaSenhora");

        x.adicionarLoja(new LojaRestauracao("aa", 50, 8000f, 10, 10, 10f));

        boolean expected = true;

        Printer.listarLojasTodas(x);

        assertTrue(expected == x.removerLojaID(0));
    }

    @Test
    void testPrints()
    {
        CentroComercial x = new CentroComercial();

        x.setNome("oii");
        x.setMorada("R.NossaSenhora");

        x.adicionarLoja(new LojaRestauracao("aa", 50, 8000f, 10, 10, 10f));
        x.adicionarLoja(new AncoraExterna("aa",200,500f,20,200f));

        Printer.listarLojasAncora(x);
        Printer.listarLojasSoNome(x);
        Printer.listarLojasComuns(x);
    }

    @Test
    void testFileManagerSave() throws IOException
    {
        CentroComercial x = new CentroComercial();
        x.setNome("Shopping");
        x.setMorada("ola");

        x.adicionarLoja(new LojaRestauracao("pp", 40, 500.20f, 10, 10, 10f));
        x.adicionarLoja(new AncoraExterna("loja", 150, 1500f, 20, 300f));
        x.adicionarLoja(new LojaQuiosque("lojaQ", 100, 50f, 80));
        x.adicionarLoja(new AncoraPropria("super ancora", 200, 1424.34f, 283.5f));
        x.adicionarLoja(new AncoraExterna());


        FileManager.saveFile("testFile.txt", x);

        File f = new File("testFile.txt");

        assertTrue(f.exists());
    }

    @Test
    void testFileManagerLoad() throws FileNotFoundException
    {
        CentroComercial x = FileManager.loadFile("testFile.txt");

        int expected = 5;
        int actual = x.getTotalLojas();

        assertEquals(expected, actual);
    }

    @Test
    void testFileManagerLoadError() throws FileNotFoundException
    {
        CentroComercial x = FileManager.loadFile("testFileErrors.txt");

        int expected = 3;
        int actual = x.getTotalLojas();

        assertEquals(expected, actual);
    }

    @Test
    void testFileManagerSaveError() throws IOException
    {
        CentroComercial x = FileManager.loadFile("testFileErrors.txt");

        FileManager.saveFile("testFileErrorsCorrigido.txt", x);

        File f = new File("testFileErrorsCorrigido.txt");

        assertTrue(f.exists());
    }

    @Test
    void testComparatorArea()
    {
        CentroComercial c = new CentroComercial("shopping", "santa luzia");
        Loja j = new AncoraPropria("loja", 550, 29124f, 150f);
        Loja j2 = new AncoraPropria("loja2", 220, 29124f, 150f);
        Loja j3 = new LojaQuiosque("q3", 100, 500f, 10);

        c.adicionarLoja(j);
        c.adicionarLoja(j3);
        c.adicionarLoja(j2);

        c.ordenarPor(new SortArea());

        Loja expected = j3;
        Loja actual = c.obterLoja(0);
        assertEquals(expected, actual);
    }

    @Test
    void testComparatorType()
    {
        CentroComercial c = new CentroComercial("shopping", "santa luzia");
        Loja j = new AncoraPropria("loja", 550, 29124f, 150f);
        Loja j2 = new AncoraExterna("ola", 200, 200, 13, 200);
        Loja j3 = new LojaQuiosque("q3", 100, 500f, 10);


        c.adicionarLoja(j);
        c.adicionarLoja(j3);
        c.adicionarLoja(j2);

        c.ordenarPor(new SortType());
        Loja expected = j2;
        Loja actual = c.obterLoja(0);

        assertEquals(expected, actual);
    }


    @Test
    void testSortID()
    {
        CentroComercial c = new CentroComercial("shopping", "santa luzia");
        Loja j = new AncoraPropria("loja", 550, 29124f, 150f);
        Loja j2 = new AncoraExterna("ola", 200, 200, 13, 200);
        Loja j3 = new LojaQuiosque("q3", 100, 500f, 10);
        Loja j4 = new LojaRestauracao("rr", 420, 500f, 10, 10, 50f);

        c.adicionarLoja(j4);
        c.adicionarLoja(j);
        c.adicionarLoja(j3);
        c.adicionarLoja(j2);

        c.ordenarPor(new SortArea());

        c.ordenarPor(new SortId());

        Loja expected = j;
        Loja actual = c.obterLoja(0);

        assertEquals(expected, actual);
    }

    @Test
    void testAncoraPropria()
    {
        AncoraPropria x = new AncoraPropria();

        x.setNome("aa");
        x.setArea(50);
        x.setCustoSeguranca(20f);

        String n = x.getNome();
        int a = x.getArea();
        float c = x.getCustoSeguranca();

        assertTrue(n.equals(x.getNome()) && a == x.getArea()&&c==20f);
    }

    @Test
    void testAncoraExterna()
    {
        AncoraExterna x = new AncoraExterna();

        x.setNome("aa");
        x.setArea(50);
        x.setRendaFixa(123);
        x.setCustoSeguranca(321);


        String n = x.getNome();
        int a = x.getArea();
        float expectedR = 123;
        float expectedCS = 321;


        float resultR = x.getRendaFixa();
        float resultCS = x.getCustoSeguranca();

        assertTrue(expectedR == resultR && expectedCS == resultCS && a==50 && n.equals("aa"));
    }

    @Test
    void testLojaQuiosque()
    {
        LojaQuiosque x = new LojaQuiosque();
        x.setArea(50);
        x.setNome("bom dia");
        x.setReceitas(500f);
        x.setNumeroFuncionarios(10);
        LojaQuiosque.setRenda(20f);

        int a = x.getArea();
        String n = x.getNome();
        float r = x.getReceitas();
        int b = x.getNumeroFuncionarios();
        float re = LojaQuiosque.getRenda();

        assertTrue(a == 50 && n.equals("bom dia") && r == 500f && b == 10 && re == x.getRendaFixaLoja());
    }

    @Test
    void testLojaRestauracao()
    {
        LojaRestauracao x = new LojaRestauracao();
        x.setArea(50);
        x.setNome("pp");
        x.setNumeroFuncionarios(40);
        x.setReceitas(500f);
        x.setCustoManutencao(100f);
        x.setNumMesas(30);
        LojaRestauracao.setValorPorMesa(10f);
        LojaRestauracao.setRendaFixa(750f);

        int a = x.getArea();
        String n = x.getNome();
        int b = x.getNumeroFuncionarios();
        float r = x.getReceitas();
        float c = x.getCustoManutencao();
        int m = x.getNumMesas();
        float m2 = LojaRestauracao.getValorPorMesa();
        float rf = LojaRestauracao.getRendaFixa();

        assertTrue(a == 50 && n.equals("pp") && r == 500f && b == 40 && c == 100f && m == 30 && m2 == 10f && rf == 750f);
    }

    @Test
    void testLojasOutras()
    {
        AncoraPropria x = new AncoraPropria();

        AncoraPropria.setAreaGrande(500);
        AncoraPropria.setAreaPequeno(40);

        assertEquals(500, Loja.getAreaGrande());
        assertEquals(40, Loja.getAreaPequeno());

        x.setArea(300);

        String expected = LojaArea.MEDIO;

        assertEquals(expected,x.getClassificacao());

        AncoraPropria x2 = new AncoraPropria();

        x2.setArea(30);

        String expected2 = LojaArea.PEQUENO;

        assertEquals(expected2,x2.getClassificacao());

        AncoraPropria x3 = new AncoraPropria();
        x3.setArea(50000);

        String expected3 = LojaArea.GRANDE;

        assertEquals(expected3,x3.getClassificacao());

        int expected4 = 10;

        assertEquals(expected4, Loja.getTotalLojas());

        AncoraExterna a3 = new AncoraExterna();
        System.out.println(a3);


    }

    @Test
    void testContagemComum()
    {
        int expected = 13;

        assertEquals(expected, Comum.getContagem());
    }

    @Test
    void testFicheiroNulo() throws FileNotFoundException
    {
        CentroComercial k = FileManager.loadFile("aijioasf");

        assertEquals(k,null);
    }

    @Test
    void testCompareToLoja()
    {
        Loja l = new LojaRestauracao();
        Loja l2 = new LojaRestauracao();

        l.setNome("aa");
        l2.setNome("bb");

        assertTrue(l.compareTo(l2) < 0);

    }

    @Test
    void testCentroComercialFinal()
    {
        CentroComercial c = new CentroComercial("aa","bb");

        c.adicionarLoja(new AncoraExterna("xxx",10,10f,20,20f));

        assertEquals(20f,c.calcularTotalSegurancaAncora());

        assertEquals(2200.1f,c.calcularRendaAncora());

        assertEquals(0, c.getLojasComuns());
        assertEquals(1, c.getLojasAncora());
        assertEquals(1, c.getTotalLojas());

        Loja nl = c.obterLoja(3934);

        assertEquals(nl, null);

        assertEquals(100f,c.calculaPercentagemReceitasLojasAncora());
        assertEquals(0f, c.calculaPercentagemReceitasLojasComuns());

        c.ordenarPorNome();

        System.out.println(c);
    }

    @Test
    void testEncontrarLojaPorId ()
    {
        CentroComercial x = new CentroComercial ();
        Loja l = new AncoraPropria ();
        x.adicionarLoja (l);
        Loja expected = l;
        Loja actual = x.encontrarLojaID (l.getId());
        assertEquals (expected, actual);
    }

    @Test
    void testNaoEncontrarLojaPorId ()
    {
        CentroComercial x = new CentroComercial ();
        Loja expected = null;
        Loja actual = x.encontrarLojaID (0);
        assertEquals (expected, actual);
    }
}