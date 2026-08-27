# Placar de Ping-Pong - Gerenciamento de Estado

## 4. Quadro Comparativo

| Abordagem | Rotação de tela | Morte do processo |
| :--- | :--- | :--- |
| **remember** | Não sobrevive | Não sobrevive |
| **ViewModel + mutableStateOf** | Sobrevive | Não sobrevive |
| **ViewModel + StateFlow** | Sobrevive | Não sobrevive |
| **ViewModel + SavedStateHandle** | Sobrevive | Sobrevive |

*(Observação: O `remember` simples não sobrevive à rotação de tela porque a Activity é recriada. Para sobreviver à rotação apenas na view, seria necessário o `rememberSaveable`.)*

## 5. Respostas das Perguntas

**1. Por que o ViewModel sozinho (etapas 2 e 3) não é suficiente para sobreviver à morte do processo, mesmo sobrevivendo à rotação de tela?**
O ViewModel é armazenado em memória de forma desvinculada do ciclo de vida direto da *Activity*, permitindo que ele permaneça intacto quando o sistema destrói e recria a interface durante a rotação da tela. No entanto, quando ocorre a "Morte do Processo" (System-initiated process death), o sistema operacional encerra todo o processo do aplicativo na memória RAM para liberar recursos. O ViewModel morre junto, e sem um mecanismo de salvamento externo ou bundle serializado, seus dados são perdidos.

**2. Qual a diferença prática entre usar mutableStateOf e StateFlow dentro do ViewModel nesta aplicação? Em algum momento essa diferença foi perceptível nos testes?**
O `mutableStateOf` é intimamente acoplado ao Jetpack Compose; quando seu valor muda, ele automaticamente agenda a recomposição da UI, simplificando o código no Composable. Já o `StateFlow` é parte da biblioteca Kotlin Coroutines, sendo uma abordagem reativa "pura" e desacoplada de frameworks de interface, exigindo a chamada de `collectAsState()` na View para funcionar. Durante os testes de execução (rotação e morte de processo), **não houve diferença perceptível no comportamento visual ou de persistência**. Ambos se comportaram de maneira idêntica.

**3. Se este placar precisasse ser salvo permanentemente (mesmo após o usuário fechar o app e abrir dias depois), qual das quatro abordagens ainda seria insuficiente, e o que seria necessário adicionar?**
Nenhuma das quatro abordagens seria suficiente. Mesmo o `SavedStateHandle` (etapa 4) sobrevive apenas a mortes de processo iniciadas pelo *sistema* (quando o app está em segundo plano e precisa liberar memória). Se o usuário "matar" o app ativamente na tela de Recentes, o Bundle do `SavedStateHandle` é descartado. Para um salvamento definitivo, seria necessário adicionar um mecanismo de **persistência em disco**, como o *Room* (banco de dados SQLite), o *DataStore* / *SharedPreferences* (arquivos chave-valor), ou salvar em um banco de dados remoto/nuvem.

**4. Na sua opinião, qual abordagem você usaria em produção para este placar e por quê?**
Para um aplicativo de placar simples, eu utilizaria a **Etapa 4 (ViewModel + SavedStateHandle usando StateFlow)**. É a abordagem mais robusta para um estado de sessão. Ela impede frustrações caso o usuário vá responder uma mensagem em outro app, o sistema mate o processo do placar no background para economizar RAM, e ele perca a pontuação ao voltar. O uso do `StateFlow` junto com o `SavedStateHandle` mantém a arquitetura limpa e independente das bibliotecas exclusivas de View do Compose dentro da camada lógica.
