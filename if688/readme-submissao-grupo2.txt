Encontramos dificuldades no entendimento geral do código.
Os parâmetros dos métodos realizados no teste de exemplo e nomes das variáveis não eram intuitivos e levamos bastante tempo para compreender o que significavam, visto que não éramos familiarizados com ele. Contudo, conseguimos progredir com o apoio do professor e dos colegas de turma.
Percebemos que o programa não responde bem aos seguintes casos:
 - A classe de teste extende de outra classe
 - A classe de teste implementa outra classe
 - O arquivo .java que contem a classe de teste também contem outra classe. Esse erro é resolvido movendo a segunda classe para um arquivo .java separado.
 - Projetos que continham pacotes com subpacotes 


 Em relação aos grafos resultantes verificamos certas anomalias no pdf mostrando as dependências dos seguintes arquivos:

 - Hardware.testArquivoPrintln (Grafo desconexo)
 - Testes no projeto de Lógica que possuem linhas de análise dentro do método main não tem seu grafo expresso corretamente. O método acessado não é ligado a nenhum outro e o restante do grafo está desconectado.
 Exemplos de testes com esse problema:
 LogicaTest.testClearClausulas
 LogicaTest.testFunctionsHorn
 
 No pdf gerado com o teste testResolucaoFNC2 podemos ver que a linha "return Functions.verifyFNC(exp);" não tem nenhuma dependência visto que o método verifyFNC só trabalha com String. Diferentemente do testResolucaoFNC que possui a linha "if (Functions.verifyFNC(exp)){" dentro do main e gera um pdf com um grafo desconectado do método alvo.  (print em anexo no projeto com o nome fnc1e2)
 O que nós fizemos foi, retirar o camando que estava dentro do main e criamos uma funcao que continha apenas o nosso metodo testado. è a única diferença entre os arquivos Resolucao.java e Resolucao2.java



--Gabriela Mota, Geovane Pereira e Manuela Barbosa
