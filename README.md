# Brash - Aplicativo de Flashcards para Aprendizado

## Visão Geral
Brash é um aplicativo para dispositivos Android desenvolvido para auxiliar estudantes no aprendizado e revisão de conceitos por meio de flashcards. Utilizando o algoritmo de repetição espaçada SuperMemo2, Brash garante a retenção eficiente do conhecimento, ajudando os usuários a estudar de maneira mais eficaz e reter melhor as informações ao longo do tempo.

O aplicativo permite a criação, organização e revisão de flashcards em diferentes baralhos de estudo (Baralhos), usando categorias personalizadas e agendamento inteligente para otimizar o aprendizado.

## Público-Alvo
Brash é destinado a:

- **Estudantes** que desejam melhorar a retenção de conhecimento e otimizar seus estudos.
- **Aprendizes** ao longo da vida que querem organizar e revisar informações de maneira eficaz com flashcards.
- **Professores** ou instrutores que desejam criar e compartilhar materiais de estudo.

## Funcionalidades Principais
1. **Cadastro e Autenticação de Usuário**
   - Criar conta, fazer login e redefinir a senha.
   - Gerenciar informações e configurações do perfil do usuário.
   - Autenticação de usuário gerenciada via Firebase Authentication.
2. **Gestão de Baralhos (Baralhos)**
   - Criar novos baralhos de estudo e organizá-los em pastas personalizadas (Pastas).
   - Visualizar e editar baralhos de estudo e os cartões contidos neles.
   - Opção para compartilhar baralhos publicamente ou mantê-los privados para uso pessoal.
   - Criar anotações para baralhos, permitindo que o usuário adicione observações ou informações adicionais a cada baralho.
3. **Cartões de Estudo (Cartões)**
   - Criar flashcards com perguntas (pergunta) e respostas (resposta).
   - Opção de anexar imagens a cada cartão para melhorar o contexto.
   - Revisar flashcards utilizando o algoritmo SuperMemo2 para garantir intervalos de revisão eficientes com base no seu nível de conhecimento.
   - Criar dicas para cada cartão, permitindo que o usuário adicione sugestões ou informações auxiliares para facilitar o aprendizado.
4. **Organização por Pastas (Pastas)**
   - Organize seus baralhos (Baralhos) em pastas personalizadas (Pastas).
   - Facilite o acesso e a gestão de materiais de estudo relacionados em um só lugar.
5. **Sistema de Revisão Inteligente**
   - Algoritmo SuperMemo2: Flashcards são revisados com base em um sistema de repetição espaçada, onde o tempo entre as revisões aumenta conforme o desempenho do usuário.
   - O usuário pode avaliar a dificuldade de cada cartão e o sistema ajusta o próximo intervalo de revisão de acordo.
6. **Baralhos Públicos e Funcionalidades Sociais**
   - Baralhos Públicos: Compartilhe e explore baralhos de flashcards criados por outros usuários.
7. **Configurações de Estudo Personalizáveis**
   Frequência de Revisão: O usuário pode definir quantos cartões novos deseja estudar por dia.

## Como Usar o Brash
**Passo 1: Cadastro e Login**
1. Crie uma conta ou faça login usando suas credenciais.
2. Após o login, gerencie seu perfil e comece a criar seus baralhos de estudo.

**Passo 2: Criando Cartões de Estudo**
1. Crie um novo baralho (Baralho) ou escolha um baralho público existente.
2. Adicione cartões de estudo (Cartões) com uma pergunta (pergunta) e resposta (resposta).
3. Se desejar, crie anotações para adicionar observações ou informações adicionais ao baralho.
4. Se desejar, crie dicas para facilitar lembrar da resposta do cartão.

**Passo 3: Organizando com Pastas**
1. Crie pastas (Pastas) para agrupar seus baralhos por tópico ou curso.
2. Organize os baralhos em pastas específicas para melhorar a gestão e o acesso.

**Passo 4: Revisando com Repetição Espaçada**
1. Revise seus flashcards diariamente utilizando o algoritmo SuperMemo2.
2. Avalie a dificuldade de cada flashcard para ajustar o intervalo de revisão para a próxima sessão.
3. À medida que avança, o sistema ajusta automaticamente a frequência das revisões para melhorar a retenção.

## Arquitetura do Sistema
O **Brash** segue uma arquitetura baseada nos padrões **MVVM (Model-View-ViewModel)** e **Camadas**, dividindo-se nos seguintes subsistemas:

- **Núcleo:** Garante as funcionalidades de cadastro, autenticação de usuários e gerenciamento do perfil do usuário.
- **Aprendizado:** Responsável pela criação e organização de flashcards, baralhos e agendamento de revisões.

### Tecnologias Utilizadas
- **Frontend:** Kotlin, Android SDK
- **Banco de Dados:** Firebase (Realtime Database para armazenar dados de usuários, baralhos e flashcards)
- **Autenticação:** Firebase Authentication
- **Design da interface do Usuário:** Figma, Visual Paradigm para wireframes de design e modelagem de sistemas
- **Controle de Versão:** GitHub para gerenciamento de código e colaboração
- **Gerenciamento de Dependências:** Gradle

## Melhorias Futuras
- **Otimização das consultas** Otimizar o tempo de resposta entre firebase e interface do usuário
- **Pesquisa Avançada:** Implementar pesquisa avançada e filtragem para baralhos e flashcards.
- **Modo Offline:** Permitir acesso offline aos flashcards e sincronizar quando o usuário reconectar à internet.
- **Suporte a Múltiplos Idiomas:** Estender o aplicativo para suportar vários idiomas e alcançar um público global.
- **Implementar as seguintes funcionalidades:** Fazer amizades entre usuários, colocações semanais baseadas em cartões revisados, visualização de estatísticas do usuário e estatísticas de baralhos específicos

## Vídeo promocional
https://youtu.be/Oy7HQAS7l5g

## Contato
Para mais informações, entre em contato com a equipe de desenvolvimento:

- Lucas Pereira Taborda: lucasptaborda@hotmail.com
- Matheus Saick de Martin: matheussaick@gmail.com
- Renzo Henrique Guzzo Leão: renzolealguzzo@gmail.com
---
**Brash** - Aprenda de forma eficiente com flashcards e repetição espaçada!

