# bertoti engenharia de software

-------------------------------------------------------------------------------- 14/2 ----------------------------------------------------------------

ATIVIDADE 1 - Esse trecho discute a diferença entre engenharia de software, programação e ciência da computação, e faz um paralelo interessante entre o campo da engenharia de software e outras engenharias tradicionais. A principal mensagem é que engenharia de software não se resume a escrever código (programação), mas sim a uma abordagem mais ampla e estruturada, que envolve o uso de princípios teóricos e metodológicos para construir aplicações.

ATIVIDADE 2 - O princípio de tempo e mudança destacam a importância de planejar o software para evoluir, o código precisará ser adaptado, modificado e ajustado à medida que as necessidades de negócios mudam.

O princípio de escala e crescimento diz sobre ser capaz de lidar com um aumento significativo na escala da aplicação, isso pode envolver a necessidade de dividir sistemas grandes, melhorar a performance ou até adotar novas arquiteturas.

O princípio de compensação e custos se refere como as decisões precisam ser tomadas dentro de um contexto de trade-offs.

ATIVIDADE 3 - Desempenho: Para otimizar o desempenho, pode ser necessário escrever códigos mais complexos, que seja mais difícil de entender e manter. Isso pode envolver técnicas avançadas ou soluções específicas de baixo nível para melhorar a velocidade ou a utilização de recursos.

Escalabilidade: Um sistema altamente escalável pode lidar com grandes volumes de dados ou usuários. Para alcançar isso, muitas vezes é necessário projetar a arquitetura de forma complexa, com componentes distribuídos, microserviços ou múltiplos servidores, o que pode aumentar o custo para manter o sistema.

Segurança: Medidas rigorosas de segurança, como autenticação multifatorial, criptografia e verificações de permissão, são essenciais para proteger dados e prevenir vulnerabilidades. No entanto, essas medidas podem tornar o sistema mais complexo e menos conveniente para o usuário final.

-------------------------------------------------------------------------------- 21/2: ------------------------------------------------------------------

ATIVIDADE 4 -> diagrama UML (Imágem do código no link).

diagrama UML:

![image](https://github.com/user-attachments/assets/fc9d4cd7-d3e2-44a7-b64f-dd140d41b2ef)

----------------------------------------------------------------------------------------17/03--------------------------------------------------------------

ATIVIDADE 5 -> Java (Imagem do código no link).

![image](https://github.com/user-attachments/assets/3234e3d4-294f-4bd7-9f95-19028475a447)
![image](https://github.com/user-attachments/assets/4ea07081-bbdb-41c7-804c-557540f30314)
![image](https://github.com/user-attachments/assets/7a05df75-46b5-4f33-94d1-cdd8ef9b8e72)

---------------------------------------------------------------------------
ATIVIDADE 6 -> TESTES COM JUNITY ASSERTEQUALS

`import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EstacionamentoTest {

    private Estacionamento estacionamento;

    @BeforeEach
    public void setUp() {
        estacionamento = new Estacionamento();
    }

    @Test
    public void testAddPlaca() {
        // Arrange
        Placa placa = new Placa("ABC1234");

        // Act
        estacionamento.addPlaca(placa);
        List<Placa> placas = estacionamento.getPlacas();

        // Assert
        assertEquals(1, placas.size());
        assertEquals("ABC1234", placas.get(0).getPlaca());
    }

    @Test
    public void testBuscarPlacaExistente() {
        // Arrange
        Placa placa1 = new Placa("ABC1234");
        Placa placa2 = new Placa("XYZ9876");
        Placa placa3 = new Placa("ABC1234"); // Placa duplicada propositalmente

        estacionamento.addPlaca(placa1);
        estacionamento.addPlaca(placa2);
        estacionamento.addPlaca(placa3);

        // Act
        List<Placa> placasEncontradas = estacionamento.buscarPlaca("ABC1234");

        // Assert
        assertEquals(2, placasEncontradas.size());
        assertEquals("ABC1234", placasEncontradas.get(0).getPlaca());
        assertEquals("ABC1234", placasEncontradas.get(1).getPlaca());
    }

    @Test
    public void testBuscarPlacaInexistente() {
        // Arrange
        Placa placa = new Placa("ABC1234");
        estacionamento.addPlaca(placa);

        // Act
        List<Placa> placasEncontradas = estacionamento.buscarPlaca("XYZ9876");

        // Assert
        assertTrue(placasEncontradas.isEmpty());
    }

    @Test
    public void testSetPlaca() {
        // Arrange
        Placa placa = new Placa("ABC1234");

        // Act
        String oldId = placa.setPlaca("XYZ9876");

        // Assert
        assertEquals("ABC1234", oldId);
        assertEquals("XYZ9876", placa.getPlaca());
    }

    @Test
    public void testBuscarAposModificarPlaca() {
        // Arrange
        Placa placa = new Placa("ABC1234");
        estacionamento.addPlaca(placa);

        // Act
        placa.setPlaca("XYZ9876");
        List<Placa> placasAntigas = estacionamento.buscarPlaca("ABC1234");
        List<Placa> placasNovas = estacionamento.buscarPlaca("XYZ9876");

        // Assert
        assertTrue(placasAntigas.isEmpty());
        assertEquals(1, placasNovas.size());
        assertEquals("XYZ9876", placasNovas.get(0).getPlaca());
    }
`

