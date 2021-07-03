# Genetic algorithms
> Here is an example implementation of a genetic algorithm in Java.

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Status](#status)
* [Contact](#contact)

## General info
1. Basic GA.
GA simulate the evolutionary process guided by the principle "survival of the fittest". In GA this principle is applied as "recombination of the fittest".Good genes have more opportunities to reproduce. As the reproduction progress the biological population will converge towards a trend.
Given a set of 5 genes, each gene can hold one of the binary values 0 and 1.
The fitness value is calculated as the number of 1s present in the genome. If there are five 1s, then it is having maximum fitness. If there are no 1s, then it has the minimum fitness.
This genetic algorithm tries to maximize the fitness function to provide a population consisting of the fittest individual, i.e. individuals with five 1s.
Note: In this example, after crossover and mutation, the least fit individual is replaced from the new fittest offspring.

2. Improved version of GA.
This version use OOP and print in the console fitness score values.

3. GA to solve maze path finding.

Use GA to solve maze path finding problem. The programm can ontain optimal solution (but not necessary) by simulating genetic hibridization and mutation. Rulette algorithm is used for selection.

## Screenshots
GA to solve maze path finding.
[Click-me!](https://github.com/danipedro2006/Spring-Boot-CRUD-demo-project/blob/master/FRqeKMwI4D.gif)

## Technologies
* Java - version 1.8

## Status
* Project is: under development. 

## Contact
Created by @Danisoft Arad 2020(https://danipedro2006.github.io/) - feel free to contact me!