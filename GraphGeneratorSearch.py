import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

# Leitura do arquivo CSV
file_path = 'ResultsSearch.csv'
df = pd.read_csv(file_path)

# Listar todas as bases de dados únicas
bases_de_dados = df['Base de Dados'].unique()

# Função para plotar gráficos para cada base de dados
def plot_graphs_for_base(base_de_dados):
    # Filtrar o DataFrame para a base de dados atual
    filtered_df = df[df['Base de Dados'] == base_de_dados]
    
    # Pivotar o DataFrame para facilitar o plot
    pivot_df = filtered_df.pivot(index='Base de Dados Pesquisa', columns='Algoritmo', values='Tempo').reset_index()
    pivot_df = pivot_df.melt(id_vars=['Base de Dados Pesquisa'], value_vars=['Arvore AVL', 'Vermelha e Preta', 'Splay'], 
                             var_name='Algoritmo', value_name='Tempo')
    
    # Plot: Comparação de Tempo por Base de Dados de Pesquisa
    plt.figure(figsize=(12, 6))
    sns.barplot(x='Base de Dados Pesquisa', y='Tempo', hue='Algoritmo', data=pivot_df)
    plt.yscale('log')
    plt.title(f'Comparação de Tempo por Algoritmo e Base de Dados de Pesquisa - {base_de_dados}')
    plt.xlabel('Base de Dados de Pesquisa')
    plt.ylabel('Tempo')
    plt.legend(title='Algoritmo')
    plt.xticks(rotation=45, ha='right')
    plt.tight_layout()
    plt.savefig(f'./Gráficos/Comparacao_Tempo_BaseDadosPesquisa_{base_de_dados.replace(" ", "_")}.png')
    plt.close()

# Gerar gráficos para cada base de dados
for base in bases_de_dados:
    plot_graphs_for_base(base)
