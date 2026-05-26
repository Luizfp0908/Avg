# AVV Performance

O **AVV Performance** é um sistema de gestão de desempenho esportivo desenvolvido para a Associação Vila Velha de Vôlei (AVV). O software permite o controle integral de atletas, jogos, e a geração de estatísticas (scout) fundamentais para a análise técnica da comissão técnica.

## 🚀 Funcionalidades

- **Gestão de Atletas:** Cadastro e controle de elenco com informações detalhadas.
- **Gestão de Jogos:** Registro de partidas, placares e adversários.
- **Scout Técnico:** Mapeamento de desempenho por fundamento durante as partidas.
- **Análise Inteligente:** Geração de relatórios técnicos em **PDF** para facilitar a tomada de decisão da comissão técnica.
- **Identidade Visual:** Interface moderna e intuitiva focada na experiência do usuário (UI/UX).

## 🛠️ Tecnologias Utilizadas

Este projeto foi desenvolvido utilizando boas práticas de Engenharia de Software:

- **Linguagem:** Java 17+
- **Interface:** JavaFX (MVC Pattern)
- **Persistência:** SQLite com DAO (Data Access Object)
- **Relatórios:** iTextPDF
- **Controle de Versão:** Git/GitHub
- **Estilização:** CSS customizado para interface moderna

## 🏗️ Arquitetura do Projeto

O sistema segue o padrão **MVC (Model-View-Controller)** para garantir a independência entre as camadas:

* `model`: Entidades de negócio (Atleta, Jogo, Scout).
* `controller`: Lógica de interface e navegação.
* `service`: Regras de negócio e processamento de dados.
* `dao`: Camada de acesso ao banco de dados SQL.
* `database`: Inicialização e povoamento do banco (DataSeeder).

## 💻 Como Executar

1. Clone este repositório:
   ```bash
   git clone [https://github.com/Luizfp0908/Avg.git](https://github.com/Luizfp0908/Avg.git)
