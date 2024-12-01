# Aplicativo de Consulta de CEP - ViaCEP

Este projeto é um aplicativo Android desenvolvido em Kotlin que utiliza a API pública do ViaCEP para consultar endereços a partir de um CEP informado. O aplicativo apresenta funcionalidades como consulta de CEP, armazenamento local dos resultados, exportação dos dados em formato CSV e compartilhamento.

## Funcionalidades do Aplicativo

- Consulta de CEP: Busca dados como rua, bairro, cidade e estado usando a API pública do ViaCEP.
- Armazenamento Local: Salva os resultados das consultas localmente usando o banco de dados SQLite.
- Exportação de Dados: Permite exportar os resultados em formato CSV para fácil compartilhamento.
- Interface Moderna: Interface construída apenas com Jetpack Compose, sem o uso de XML.

## Estrutura do Projeto

O projeto segue uma organização simples com todos os arquivos Kotlin localizados no pacote com.example.viacep.

### Principais Componentes

1. Tela Principal
   - Campo de entrada para o CEP.
   - Botões para pesquisar, limpar histórico, gerar CSV e compartilhar CSV.
   - Exibição dos resultados armazenados em uma lista.

2. Requisição à API ViaCEP
   - Implementação da conexão e consulta à API pública do ViaCEP.
   - Tratamento de erros como CEP inválido ou não encontrado.

3. Banco de Dados Local
   - Uso do SQLite para armazenar os resultados das consultas.
   - Persistência dos dados entre as sessões do aplicativo.

4. Exportação e Compartilhamento de CSV
   - Geração de um arquivo CSV com os resultados armazenados.
   - Compartilhamento seguro do arquivo usando o FileProvider.

## Executando o Projeto

1. Pré-requisitos
   - Android Studio instalado (versão mínima recomendada: Arctic Fox ou superior).
   - Dispositivo físico ou emulador configurado para rodar aplicativos Android.

2. Clonando o Repositório
   - Clone o repositório do projeto para o seu ambiente local:
     git clone <link-do-repositorio>

3. Abrindo no Android Studio
   - Abra o Android Studio e importe o projeto.

4. Executando no Emulador ou Dispositivo
   - Conecte um dispositivo físico ou inicie um emulador.
   - Clique no botão Run para instalar e iniciar o aplicativo.

## Permissões Necessárias

Adicione as seguintes permissões no arquivo AndroidManifest.xml para permitir o funcionamento completo do aplicativo:

<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />

## Estrutura de Arquivos

- MainActivity.kt: Gerencia a interface principal e eventos do usuário.
- AddressRepository.kt: Controla a lógica de armazenamento e recuperação de dados do SQLite.
- ExportUtils.kt: Responsável por gerar e compartilhar o arquivo CSV.
- ApiService.kt: Implementa a comunicação com a API ViaCEP.

## Resultados e Relatórios

1. Consulta de CEP
   - Resultados exibidos em tempo real na interface.

2. Exportação CSV
   - Arquivo gerado no diretório:
     /Android/data/com.example.viacep/files/Documents/addresses.csv

3. Compartilhamento CSV
   - Botão de compartilhamento permite enviar o arquivo diretamente para outros aplicativos.

## Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou enviar pull requests com sugestões de melhorias.

## Licença

Este projeto está licenciado sob a MIT License.

## Time do Projeto

- Dilermando Afonso
- Labelle Cândido
- Michael Azevedo
- Rafael "A lenda" Diniz
