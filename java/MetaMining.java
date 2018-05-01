/*
 * MetaMining.java
 *
 * Created on: May/01/2018
 * Author: Peter Frank Perroni (pfperroni@gmail.com)
 */

import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.util.Scanner;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;
import java.util.Random;
import java.util.Locale;
import java.io.PrintWriter;

/**
 * @brief Template for students to develop a metaheuristic search, based on a clustering algorithm.
 * 
 * Required WEKA tool.
 */
public class MetaMining {
    public static void main(String args[]){
		if(args.length != 9){
			System.out.println("Faltou parâmetro: <shift-file> <k> <n> <p> <w> <c1> <c2> <s> <func>");
			return;
		}
		try {
			double nextPositions[][] = new double[p][n];
			double pbPositions[][];

			// Lê os parâmetros do usuário.
			// ...

			// Lê o arquivo de shift e multiplica por s.
			// ...

			// Inicializa a função de minimização.
			// ...

			// Inicializa o PSO, passando a função de minimização.
			PSO pso = new PSO(s, func, p, n, w, c1, c2);
			pso.setMaxEvals((int)1e6);

			// Procede com 1 execução do PSO.
			while(true){
				// Executa 2 * n iterações do PSO.
				pso.next(2 * n);
				
				// Verifica se o fitness melhorou.
				// ...

				// Lê os melhores pessoais de cada indivíduo.
				pbPositions = pso.getPBestPositions();

				// Monta o arquivo para o WEKA:
				// ... monta o cabeçalho.
				// ...

				// ... monta a lista de dados (linhas = indivíduos, colunas = cada dimensão do pbPositions).
				// ...
				
				// ... redireciona os streams.
				// ...

				// ... chama o algoritmo de clusterização do WEKA (SimpleKMeans).
				// ...

				// ... restaura os streams.
				// ...
				
				// ... lê os centróides do texto retornado pelo WEKA, preenchendo as próximas posições em nextPostions.
				// ...
				
				// Reposiciona os indivíduos do PSO nas novas posições.
				pso.setPositions(nextPositions);
			}
			System.out.println("Resultado: ");
			double gb[] = pso.getGBestPosition();
			for(i=0; i < n; i++) System.out.print(gb[i] + ", ");
			System.out.println("\nFitness:  " + pso.getGBestFitness());
			System.out.println("N.Evaluations:  " + pso.getNEvals());
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
    }
}
