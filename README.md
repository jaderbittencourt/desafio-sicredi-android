Desafio Digital
========

Desafio de desenvolvimento Android para a empresa [Sicredi - Digital][1]

Desenvolvido por [Jader Bittencourt][2]

Justificativa das Libs e Frameworks utilizados
--------

**Retrofit2** 
- Por ser amplamente utilizada no mercado e já ser uma lib praticamente "padrão" quando trata-se de REST APIs.

**Picasso** 
- Facilitar o download e cache de imagens, permitindo otimizar o desenvolvimento e utilizar recursos como placeholder para imagens inexistentes ou cujo download ainda não foi feito.

**OkHttp** 
- Utilizado em conjunto com **Retrofit2** e **Picasso**.

**Gson** 
- Conversão de objetos em Json e vice-versa. Também trabalha bem junto do **Retrofit2**.

**Dagger** 
- Ótimo framework de DI e por funcionar bem com Android.

**Lombok** 
- Para evitar boilerplate code

**Circular image view** 
- Imagems em forma circular prontas. Para que reinventar a roda? =) 

Arquitetura
-------------
Escolhida MVVM por ser bastante eficar para o  Android e por tornar a organização do código melhor, separando bem as responsabilidades.

 [1]: https://sicredifazadiferenca.com.br/
 [2]: https://www.linkedin.com/in/jaderbittencourt/
