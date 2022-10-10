# Integração simples utilizando um modelo de api da marvel

Integração simples utilizando um modelo de api da marvel Este projeto foi desenvolvido de forma
modularizada e sempre utilizado as bibliotecas recomendadas pelo Google do 
[Android Jetpack](https://developer.android.com/jetpack), desta forma temos um código muito mais robusto,
testável, manutenível e escalável.

Como o projeto foi desenvolvido de forma modularizada, precisaríamos de uma maneira segura e eficaz
de gerenciar as dependências de todos os módulos dentro do projeto, e para isso foi utilizado um
conceito de buildSrc, assim gerenciar todas as dependências de todos os módulos do projeto, fica
muito mais fácil, organizado e centralizado em um único local.

O funcionamento do app ocorre e modo online/offline, e para que a navegação pudesse ocorrer em modo
offline, foi utilizado a lib Room para trabalhamos com o banco de dados de maneira muito mais
simples e objetiva, e para que as chamadas com o banco de dados pudessem ocorrer de forma isolada,
foi utilizado o conceito de Coroutines, assim o a inserção/deleção de dados ocorre fora da thread
principal de maneira muito mais simples e segura.

Para realizar a navegação entre telas, foi uma das bibliotecas oficias do Android JetPack, o
Navigation, dessa forma, navegar entre telas será muito mais fácil e organizado.

Para a integração do modelo da api da Marvel a qual foi utilizado nesse projeto, foi utilizado o
Retrofit, e para realizar download/cache das imagens, foi utilizado o Glide para fazer este
trabalho.

Para ouvirmos eventos de desconexão/conexão de internet, utilizei a lib ReactiveNetwork, assim
monitorar esses eventos se tornam bem mais prático e intuitivo.

Para deixar um visual um pouco mais agradável e intuitivo, utilizei a lib Lottie para deixar as
coisas um pouco mais animadas.

E por fim, para deixar o apk um pouco mais seguro e evitar/dificultar possíveis engenharia reversa,
ativamos o ProGuard para ofuscar todo o código do apk é também remover possíveis arquivos que não
são utilizados, assim temos uma compilação resultando em um apk bem menor.
