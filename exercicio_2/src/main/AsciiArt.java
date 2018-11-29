package main;

import java.io.*;
import java.util.ArrayList;

public class AsciiArt {
	ArrayList<Runs> imagem;
	
	public AsciiArt () {
		imagem = new ArrayList<>();
	}
	
	public void carregaImagemRaw(String nameImg) throws IOException {
		FileReader file = null;
		BufferedReader in = null;
		String linha = "";
		try {			
			file = new FileReader(nameImg + ".txt");
			in = new BufferedReader(file);
			
			while (linha !=  null) {
				linha = in.readLine();
				
				if(linha != null) {
					compactaRLE(linha);	
				}
			}
		} catch (IOException e) {
			e.toString();
		} finally {
			file.close();
			in.close();
		}
	}
	
	public void visualizaImagem() {
		String linha = "";
		for(int i = 0; i < imagem.size(); i++) {
			int cont = 0;
			int comprimento = imagem.get(i).getComprimento();
			while(cont < comprimento) {
				linha += imagem.get(i).getSimbolo() == 'n' ? "\n" : imagem.get(i).getSimbolo();
				cont++;	
			}
		}
		System.out.print(linha);
	}
	
	public void armazenaImagemRLE() throws IOException {
		try {
			RandomAccessFile f = new RandomAccessFile("salvaArrayList.txt", "rw");
			f.seek(0);
			for(int i = 0; i < imagem.size() ; i++) {
				f.write(imagem.get(i).getComprimento());
				f.write(imagem.get(i).getSimbolo());
			}
			f.seek(0);
			f.close();
		}
		catch(IOException e){}
	}
	
	public void carregaImageRLE() {
		try {
			RandomAccessFile f = new RandomAccessFile("salvaArrayList.txt", "rw");
			for(int i = 0; i < f.length(); i++) {
				imagem.add(new Runs(f.readByte(), (char)f.readByte()));
			}
			for(int i = 0; i < f.length(); i++)
				System.out.println(f.readByte() + "" + f.readByte());
			f.seek(0);
			f.close();
		} catch(IOException e) {
			
		}
	}
	
	private void compactaRLE(String linha) {
		int count; 
		
		for (int i = 0; i < linha.length(); i++) {
			count = 1;
			
			while (i + 1 < linha.length() && linha.charAt(i) == linha.charAt(i + 1)) {
				count++;
				i++;
			}
			imagem.add(new Runs(count, linha.charAt(i)));
		}
		
		imagem.add(new Runs(1, 'n'));
	}
	
	public void printTeste() {
		for(int i = 0; i < imagem.size(); i++) {
			System.out.println(imagem.get(i).getComprimento() + "S: " + imagem.get(i).getSimbolo());
		}
			
	}
	
}
