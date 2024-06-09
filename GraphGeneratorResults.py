import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

# Leitura do arquivo CSV
file_path = 'ResultsSearch.csv'
df = pd.read_csv(file_path)

# Função para plotar gráficos para dados de diferentes tamanhos (1M, 2M, 3M)
def plot_graphs_for_size(df, size):
    # Filtrar o DataFrame para o tamanho atual
    filtered_df = df[df['Base de Dados'].str.startswith(size)]
    
    # Plot: Comparação de Tempo por Base de Dados
    plt.figure(figsize=(12, 6))
    sns.barplot(x='Base de Dados', y='Tempo', hue='Algoritmo', data=filtered_df)
    plt.yscale('log')
    plt.title(f'Comparação de Tempo por Algoritmo e Base de Dados - {size}')
    plt.xlabel('Base de Dados')
    plt.ylabel('Tempo (ns)')
    plt.legend(title='Algoritmo')
    plt.xticks(rotation=45, ha='right')
    plt.tight_layout()
    plt.savefig(f'./Gráficos/Comparacao_Tempo_BaseDados_{size}.png')
    plt.close()

    # Plot: Comparação de Rotações por Base de Dados
    plt.figure(figsize=(12, 6))
    sns.barplot(x='Base de Dados', y='Rotations', hue='Algoritmo', data=filtered_df)
    plt.yscale('log')
    plt.title(f'Comparação de Rotações por Algoritmo e Base de Dados - {size}')
    plt.xlabel('Base de Dados')
    plt.ylabel('Rotações')
    plt.legend(title='Algoritmo')
    plt.xticks(rotation=45, ha='right')
    plt.tight_layout()
    plt.savefig(f'./Gráficos/Comparacao_Rotacoes_BaseDados_{size}.png')
    plt.close()

    # Plot: Comparação de Altura por Base de Dados
    plt.figure(figsize=(12, 6))
    sns.barplot(x='Base de Dados', y='Height', hue='Algoritmo', data=filtered_df)
    plt.yscale('log')
    plt.title(f'Comparação de Altura por Algoritmo e Base de Dados - {size}')
    plt.xlabel('Base de Dados')
    plt.ylabel('Altura')
    plt.legend(title='Algoritmo')
    plt.xticks(rotation=45, ha='right')
    plt.tight_layout()
    plt.savefig(f'./Gráficos/Comparacao_Altura_BaseDados_{size}.png')
    plt.close()

# Plotar gráficos para 1M, 2M e 3M
for size in ["1M", "2M", "3M"]:
    plot_graphs_for_size(df, size)
