# Integração simples utilizando a api da marvel

O funcionamento do app ocorre e modo online/offline, e para que a navegação pudesse ocorrer
em modo offline, utilizei a lib Room para armazenar esses dados em um banco de dados, e para
que esse armazenamento pudesse ocorrer em background, foi utilizado o conceito de
Coroutines, assim o a inserção/deleção de dados ocorre fora da thread principal de maneira
muito mais simples.

Para salvarmos dados fora do banco, dados pequenos no qual não requer o uso do banco de
dados, utilizei a lib Hawk, com ela podemos armazenar os dados de forma criptografada em um
arquivo de shared preferences.

A integração da api da Marvel a qual foi utilizado nesse projeto, foi utilizado o Retrofit,
e para realizar download/cache das imagens, utilizei o Glide para fazer este trabalho.

Para ouvirmos eventos de desconexão/conexão de internet, utilizei a lib RxNetwork, assim
monitorar esses eventos se tornam bem mais prático e intuitivo.

Para deixar um visual um pouco mais agradável e intuitivo, utilizei a lib Lottie para deixar as
coisas um pouco mais animadas.

E por fim para deixar o apk um pouco mais seguro e evitar/dificultar possíveis engenharia
reversa, ativamos o ProGuard para ofuscar todo o código do apk é também remover possíveis
arquivos que não são utilizados, assim temos uma compilação resultando em um apk bem
menor.

OBS: O app foi desenvolvido todo no idioma inglês, eu resolvi seguir esse padrão por causa da
api da Marvel utilizada no desafio, que já retorna estes dados em inglês.

