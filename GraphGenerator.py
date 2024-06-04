import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

# Leitura do arquivo CSV
file_path = 'ResultsInsertion.csv'
df = pd.read_csv(file_path)

def plot_graphs(df, algoritmo):
    # Line plot: Tempo vs Rotações
    plt.figure(figsize=(10, 6))
    sns.lineplot(x='Rotations', y='Tempo', hue='Algoritmo', style='Algoritmo', markers=True, data=df)
    plt.title(f'Tempo vs Rotações - {algoritmo}')
    plt.xlabel('Rotações')
    plt.ylabel('Tempo (ns)')
    plt.legend(title='Algoritmo')
    plt.savefig(f'Gráficos/Tempo_vs_Rotacoes_{algoritmo}.png')
    plt.close()

    # Line plot: Tempo vs Altura
    plt.figure(figsize=(10, 6))
    sns.lineplot(x='Height', y='Tempo', hue='Algoritmo', style='Algoritmo', markers=True, data=df)
    plt.title(f'Tempo vs Altura - {algoritmo}')
    plt.xlabel('Altura')
    plt.ylabel('Tempo (ns)')
    plt.legend(title='Algoritmo')
    plt.savefig(f'Gráficos/Tempo_vs_Altura_{algoritmo}.png')
    plt.close()

    # Line plot: Rotações vs Altura
    plt.figure(figsize=(10, 6))
    sns.lineplot(x='Height', y='Rotations', hue='Algoritmo', style='Algoritmo', markers=True, data=df)
    plt.title(f'Rotações vs Altura - {algoritmo}')
    plt.xlabel('Altura')
    plt.ylabel('Rotações')
    plt.legend(title='Algoritmo')
    plt.savefig(f'Gráficos/Rotacoes_vs_Altura_{algoritmo}.png')
    plt.close()

    # Bar plot: Comparação de Tempo por Base de Dados
    plt.figure(figsize=(10, 6))
    sns.barplot(x='Base de Dados', y='Tempo', hue='Algoritmo', data=df)
    plt.title(f'Comparação de Tempo por Algoritmo e Base de Dados - {algoritmo}')
    plt.xlabel('Base de Dados')
    plt.ylabel('Tempo (ns)')
    plt.legend(title='Algoritmo')
    plt.xticks(rotation=45, ha='right')  # Rotaciona os rótulos do eixo x
    plt.tight_layout()  # Ajusta o layout para que os rótulos não sejam cortados
    plt.savefig(f'Gráficos/Comparacao_Tempo_BaseDados_{algoritmo}.png')
    plt.close()

    # Bar plot: Comparação de Rotações por Base de Dados
    plt.figure(figsize=(10, 6))
    sns.barplot(x='Base de Dados', y='Rotations', hue='Algoritmo', data=df)
    plt.title(f'Comparação de Rotações por Algoritmo e Base de Dados - {algoritmo}')
    plt.xlabel('Base de Dados')
    plt.ylabel('Rotações')
    plt.legend(title='Algoritmo')
    plt.xticks(rotation=45, ha='right')  # Rotaciona os rótulos do eixo x
    plt.tight_layout()  # Ajusta o layout para que os rótulos não sejam cortados
    plt.savefig(f'Gráficos/Comparacao_Rotacoes_BaseDados_{algoritmo}.png')
    plt.close()

    # Bar plot: Comparação de Altura por Base de Dados
    plt.figure(figsize=(10, 6))
    sns.barplot(x='Base de Dados', y='Height', hue='Algoritmo', data=df)
    plt.title(f'Comparação de Altura por Algoritmo e Base de Dados - {algoritmo}')
    plt.xlabel('Base de Dados')
    plt.ylabel('Altura')
    plt.legend(title='Algoritmo')
    plt.xticks(rotation=45, ha='right')  # Rotaciona os rótulos do eixo x
    plt.tight_layout()  # Ajusta o layout para que os rótulos não sejam cortados
    plt.savefig(f'Gráficos/Comparacao_Altura_BaseDados_{algoritmo}.png')
    plt.close()

treeNames = ["arvore AVL", "Vermelha e Preta", "Splay"]

for name in treeNames:
    # Filtrar o DataFrame para o algoritmo atual
    filtered_df = df[df['Algoritmo'] == name]
    # Plotar gráficos para o DataFrame filtrado
    plot_graphs(filtered_df, name)
