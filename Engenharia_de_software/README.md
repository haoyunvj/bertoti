# bertoti engenharia de software

-------------------------------------------------------------------------------- 14/2 ----------------------------------------------------------------

1 - Esse trecho discute a diferença entre engenharia de software, programação e ciência da computação, e faz um paralelo interessante entre o campo da engenharia de software e outras engenharias tradicionais. A principal mensagem é que engenharia de software não se resume a escrever código (programação), mas sim a uma abordagem mais ampla e estruturada, que envolve o uso de princípios teóricos e metodológicos para construir aplicações.

2 - O princípio de tempo e mudança destacam a importância de planejar o software para evoluir, o código precisará ser adaptado, modificado e ajustado à medida que as necessidades de negócios mudam.

O princípio de escala e crescimento diz sobre ser capaz de lidar com um aumento significativo na escala da aplicação, isso pode envolver a necessidade de dividir sistemas grandes, melhorar a performance ou até adotar novas arquiteturas.

O princípio de compensação e custos se refere como as decisões precisam ser tomadas dentro de um contexto de trade-offs.

3 - Desempenho: Para otimizar o desempenho, pode ser necessário escrever códigos mais complexos, que seja mais difícil de entender e manter. Isso pode envolver técnicas avançadas ou soluções específicas de baixo nível para melhorar a velocidade ou a utilização de recursos.

Escalabilidade: Um sistema altamente escalável pode lidar com grandes volumes de dados ou usuários. Para alcançar isso, muitas vezes é necessário projetar a arquitetura de forma complexa, com componentes distribuídos, microserviços ou múltiplos servidores, o que pode aumentar o custo para manter o sistema.

Segurança: Medidas rigorosas de segurança, como autenticação multifatorial, criptografia e verificações de permissão, são essenciais para proteger dados e prevenir vulnerabilidades. No entanto, essas medidas podem tornar o sistema mais complexo e menos conveniente para o usuário final.

-------------------------------------------------------------------------------- 21/2 CÓDIGO ATIVIDADE 5: ------------------------------------------------------------------


![image](https://github.com/user-attachments/assets/3234e3d4-294f-4bd7-9f95-19028475a447)
![image](https://github.com/user-attachments/assets/4ea07081-bbdb-41c7-804c-557540f30314)
![image](https://github.com/user-attachments/assets/7a05df75-46b5-4f33-94d1-cdd8ef9b8e72)

diagrama UML:

![image](https://github.com/user-attachments/assets/fc9d4cd7-d3e2-44a7-b64f-dd140d41b2ef)


---------------------------------------------------------------------------------- Atividade 7 - SQLite --------------------------------------------------------------------
Classe Main

Arquivo: Main.java

package org.example;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Main {
    public static void main(String[] args) {
        LojaEletronico loja = new LojaEletronico();
        loja.conectar();
        loja.criarTabela();
        loja.cadastrarCelular("S25", "Android", "Samsung", "Azul Marinho", 1900.99);
        loja.cadastrarCelular("Iphone 15 Pro MAX", "IOS","Iphone", "Branco", 5699.99);
        loja.encontrarCelular();

    }
}
Classe LojaEletronico

Arquivo: LojaElettronico.java

package org.example;
import java.sql.*;

public class LojaEletronico {

    //Linkagem com meu banco de Eletrônicos
    private String url = "jdbc:sqlite:LojaEletronico.db";

    public void conectar() {
        //Tentando conectar com o link
        try (var conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Conexão com o banco de dados estabelecida.");
            }
        } catch (SQLException e) {
            System.out.println("Erro na conexão com o banco: " + e.getMessage());
        }
    }

    // Usando parametro conn para estabelecer conexão e criando a tabela
    public void criarTabela(){
        String sql = "CREATE TABLE IF NOT EXISTS celular(\n"
                + "nome TEXT PRIMARY KEY, \n"
                + "so TEXT NOT NULL, \n"
                + "marca TEXT NOT NULL, \n"
                + "cor TEXT NOT NULL, \n"
                + "preco DOUBLE NOT NULL \n"
                + ");";
        // Verificação se há a tabela foi criada através da Classe t
        try(var conn = DriverManager.getConnection(url);
            var stmt = conn.createStatement()){
            stmt.execute(sql);
            System.out.println("Tabela criada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Tabela não foi criada" + e.getMessage());
        }
    }
    //Processo para cadastrar celulares
    public void cadastrarCelular(String nome, String so, String marca, String cor, Double preco){
        String sql = "INSERT INTO celular(nome, so, marca, cor, preco) VALUES(?,?,?,?,?)";

        //Verificar a inserção de dados
        try (var conn = DriverManager.getConnection(url);
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, so);
            stmt.setString(3, marca);
            stmt.setString(4, cor);
            stmt.setDouble(5, preco);
            System.out.println("Celular cadastrado com sucesso!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    public void encontrarCelular(){
        String sql = "SELECT * FROM celular ";

        try(var conn = DriverManager.getConnection(url);
            var stmt = conn.createStatement();

            var rs = stmt.executeQuery(sql)){
            System.out.println(rs);
            while (rs.next()){
                System.out.println("------------------------------");
                System.out.println(rs.getString("nome"));
                System.out.println(rs.getString("so"));
                System.out.println(rs.getString("marca"));
                System.out.println(rs.getString("cor"));
                System.out.println(rs.getDouble("preco"));
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
POM.XML

Arquivo: pom.xml

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>AtividadeSQLite</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>23</maven.compiler.source>
        <maven.compiler.target>23</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
    <dependencies>
    <dependency>
        <groupId>org.xerial</groupId>
        <artifactId>sqlite-jdbc</artifactId>
        <version>3.49.1.0</version>
    </dependency>
    </dependencies>

</project>
