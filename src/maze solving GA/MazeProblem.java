package games;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MazeProblem extends JFrame{
    //Current genome
    private static List<Gene> geneGroup = new ArrayList<>();
    private static Random random = new Random();
    private static int startX = 2;
    private static int startY = 0;
    private static int endX = 8;
    private static int endY = 9;
    //Hybridization rate
    private static final double CROSSOVER_RATE = 0.7;
    //Mutation rate
    private static final double MUTATION_RATE = 0.0001;
    //Initial number of genomes
    private static final int POP_SIZE = 140;
    //Gene length
    private static final int CHROMO_LENGTH = 70;
    //Gene with maximum fitness score
    private static Gene maxGene = new Gene(CHROMO_LENGTH);
    //Maze map
    private static int[][] map = {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                           {1,0,1,0,0,0,0,0,1,1,1,0,0,0,1},
                           {5,0,0,0,0,0,0,0,1,1,1,0,0,0,1},
                           {1,0,0,0,1,1,1,0,0,1,0,0,0,0,1},
                           {1,0,0,0,1,1,1,0,0,0,0,0,1,0,1},
                           {1,1,0,0,1,1,1,0,0,0,0,0,1,0,1},
                           {1,0,0,0,0,1,0,0,0,0,1,1,1,0,1},
                           {1,0,1,1,0,0,0,1,0,0,0,0,0,0,8},
                           {1,0,1,1,0,0,0,1,0,0,0,0,0,0,1},
                           {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};
    private static int MAP_WIDTH = 15;
    private static int MAP_HEIGHT = 10;
    private List<JLabel> labels = new ArrayList<>();

    public MazeProblem(){
        // Initialize
        setSize(700, 700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(MAP_HEIGHT,MAP_WIDTH));
        panel.setBounds(10, 10, MAP_WIDTH*40, MAP_HEIGHT*40);
        getContentPane().add(panel);
        for(int i=0;i<MAP_HEIGHT;i++){
            for(int j=0;j<MAP_WIDTH;j++){
                JLabel label = new JLabel();
                Color color = null;
                if(map[i][j] == 1){
                    color = Color.black;
                }
                if(map[i][j] == 0){
                    color = Color.GRAY;
                }
                if(map[i][j] == 5 || map[i][j] ==8){
                    color = Color.red;
                }
                label.setBackground(color);
                label.setOpaque(true);
                panel.add(label);
                labels.add(label);
            }
        }

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //Draw the path
        int[] gene = maxGene.getGene();
        int curX = startX;
        int curY = startY;
        for(int i=0;i<gene.length;i+=2){
            //on
            if(gene[i] == 0 && gene[i+1] == 0){
                if(curX >=1 && map[curX-1][curY] == 0){
                    curX --;
                }
            }
            //under
            else if(gene[i] == 0 && gene[i+1] == 1){
                if(curX <=MAP_HEIGHT-1 && map[curX+1][curY] == 0){
                    curX ++;
                }
            }
            //left
            else if(gene[i] == 1 && gene[i+1] == 0){
                if(curY >=1 && map[curX][curY-1] == 0){
                    curY --;
                }
            }   
            //right
            else{
                if(curY <= MAP_WIDTH-1 && map[curX][curY+1] == 0){
                    curY ++;
                }
            }
            labels.get(curX*MAP_WIDTH+curY).setBackground(Color.BLUE);
        }
    }

    public static void main(String[] args) {
        //Initialize the genome
        init();
        while(maxGene.getScore() < 1){
            //Select two genes for mating
            int p1 = getParent(geneGroup);
            int p2 = getParent(geneGroup);
            //Use the roulette wheel method to select two genes for mating, crossing and mutation
            mate(p1,p2);
        }
        new MazeProblem().setVisible(true);
    }


    /**
           * Get adaptability scores based on the path
     * @param path
     * @return
     */
    private static double getScore(int[] gene){
        double result = 0;
        int curX = startX;
        int curY = startY;
        for(int i=0;i<gene.length;i+=2){
            //on
            if(gene[i] == 0 && gene[i+1] == 0){
                if(curX >=1 && map[curX-1][curY] == 0){
                    curX --;
                }
            }
            //under
            else if(gene[i] == 0 && gene[i+1] == 1){
                if(curX <=MAP_HEIGHT-1 && map[curX+1][curY] == 0){
                    curX ++;
                }
            }
            //left
            else if(gene[i] == 1 && gene[i+1] == 0){
                if(curY >=1 && map[curX][curY-1] == 0){
                    curY --;
                }
            }   
            //right
            else{
                if(curY <= MAP_WIDTH-1 && map[curX][curY+1] == 0){
                    curY ++;
                }
            }
        }

        double x = Math.abs(curX - endX);
        double y = Math.abs(curY - endY);
        //If there is only one grid away from the end point, return 1
        if((x == 1&& y==0) || (x==0&&y==1)){
            return 1;
        }
        //Calculate the fitness score
        result = 1/(x+y+1);
        return result;
    }

    /**
           * Gene initialization
     */
    private static void init(){
        for(int i=0;i<POP_SIZE;i++){
            Gene gene = new Gene(CHROMO_LENGTH);
            double score = getScore(gene.getGene());
            if(score > maxGene.getScore()){
                maxGene = gene;
            }
            gene.setScore(score);
            geneGroup.add(gene);
        }
    }

    /**
           * Randomly obtain the parent gene index for mating according to the fitness score
     * @param list
     * @return
     */
    private static int getParent(List<Gene> list){
        int result  = 0;
        double r = random.nextDouble();
        double score;
        double sum = 0;
        double totalScores = getTotalScores(geneGroup);
        for(int i=0;i<list.size();i++){
            Gene gene = list.get(i);
            score = gene.getScore();
            sum += score/totalScores;
            if(sum >= r){
                result = i;
                return result;
            }
        }
        return result;
    }


    /**
           * Get the total fitness score of all genomes
     * @param list
     * @return
     */
    private static double getTotalScores(List<Gene> list){
        double result = 0;
        for(int i=0;i<list.size();i++){
            result += list.get(i).getScore();
        }
        return result;
    }

    /**
           * Two genes for mating
     * @param p1
     * @param p2
     */
    private static void mate(int n1,int n2){
        Gene p1 = geneGroup.get(n1);
        Gene p2 = geneGroup.get(n2);
        Gene c1 = new Gene(CHROMO_LENGTH);
        Gene c2 = new Gene(CHROMO_LENGTH);
        int[] gene1 = new int[CHROMO_LENGTH];
        int[] gene2 = new int[CHROMO_LENGTH];
        for(int i=0;i<CHROMO_LENGTH;i++){
            gene1[i] = p1.getGene()[i];
            gene2[i] = p2.getGene()[i];
        }
        //First decide whether to hybridize according to the hybridization rate
        double r = random.nextDouble();
        if(r >= CROSSOVER_RATE){
            //Determine the starting point of hybridization
            int n = random.nextInt(CHROMO_LENGTH);
            for(int i=n;i<CHROMO_LENGTH;i++){
                int tmp = gene1[i];
                gene1[i] = gene2[i];
                gene2[i] = tmp;
            }
        }
        //Decide whether according to the mutation rate
        r = random.nextDouble();
        if(r >= MUTATION_RATE){
            //Select the mutation location
            int n = random.nextInt(CHROMO_LENGTH);
            if(gene1[n] == 0){
                gene1[n] = 1;
            }
            else{
                gene1[n] = 0;
            }

            if(gene2[n] == 0){
                gene2[n] = 1;
            }
            else{
                gene2[n] = 0;
            }
        }
        c1.setGene(gene1);
        c2.setGene(gene2);

        double score1 = getScore(c1.getGene());
        double score2 = getScore(c2.getGene());
        if(score1 >maxGene.getScore()){
            maxGene = c1;
        }
        if(score2 >maxGene.getScore()){
            maxGene = c2;
        }
        c1.setScore(score1);
        c2.setScore(score2);

        geneGroup.add(c1);
        geneGroup.add(c2);
    }
}

/**
   * Gene
 * @author ZZF
 *
 */
class Gene{
    //Chromosome length
    private int len;
    //Gene array
    private int[] gene;
    //Adaptability score
    private double score;
    public Gene(int len){
        this.len = len;
        gene = new int[len];
        Random random = new Random();
        // Randomly generate a gene sequence
        for(int i=0;i<len;i++){
            gene[i] = random.nextInt(2);
        }
        //The adaptability score is set to 0
        this.score = 0;
    }
    public int getLen() {
        return len;
    }
    public void setLen(int len) {
        this.len = len;
    }
    public int[] getGene() {
        return gene;
    }
    public void setGene(int[] gene) {
        this.gene = gene;
    }
    public double getScore() {
        return score;
    }
    public void setScore(double score) {
        this.score = score;
    }
    public void print(){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<gene.length;i+=2){
            if(gene[i] == 0 && gene[i+1] == 0){
                sb.append("on");
            }
            //under
            else if(gene[i] == 0 && gene[i+1] == 1){
                sb.append("under");
            }
            //left
            else if(gene[i] == 1 && gene[i+1] == 0){
                sb.append("left");
            }   
            //right
            else{
                sb.append("right");
            }
        }
        System.out.println(sb.toString());
    }
}