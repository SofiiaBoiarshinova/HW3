package org.hibernate_test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;


public class Main {
    public static void main(String[] args) throws IOException {
        List<Tree> listTrees;
        List<TreeNode> listTreeNodes;
        System.out.println("Введите 1 для PostgreSQL, 2 для H2");
        Scanner in = new Scanner(System.in);
        int chooseDB = in.nextInt();
        Connection session = new Connection();
        session.getConnection(chooseDB);
        listTrees = session.getListTrees();
        listTreeNodes = session.getListTreeNodes();

        for (int i = 0; i < listTreeNodes.size(); i++){
            for(int j = 0; j < listTreeNodes.size(); j++){
                if(i != j){
                    if(listTreeNodes.get(i).getId() == listTreeNodes.get(j).getParentNode()){
                        listTreeNodes.get(i).addChild(listTreeNodes.get(j).getId());
                    }
                }
            }
        }
        int currentRoot;
        List<Integer> currentListOfNodes;
        List<Integer> usedNodes = new ArrayList<>();
        usedNodes.add(-1);
        //    Создание деревьев
        for(Tree tree : listTrees){
            currentRoot = tree.getRoot();
            for(TreeNode node : listTreeNodes){
                if (currentRoot == node.getId()){
                    for(int elemNode : node.getChildrenList()){
                        tree.addNode(elemNode);
                    }
                    break;
                }
            }
            currentListOfNodes = tree.getNodeList();
            int node = -1;
            boolean flag = true;
            while (flag){
                flag = false;
                for(int node0: currentListOfNodes){
                    if (usedNodes.contains(node0)){
                        continue;
                    }
                    else{
                        node = node0;
                        usedNodes.add(node);
                        flag = true;
                        break;
                    }

                }
                if(!flag){
                    break;
                }

                for(TreeNode currentNode : listTreeNodes){
                    if (currentNode.getId() == node){
                        if (currentNode.getChildrenList().isEmpty()){
                            tree.addLeaf(node);
                        }else{
                            for (int newNode : currentNode.getChildrenList()){
                                tree.addNode(newNode);
                            }
                        }
                        break;
                    }
                }
                currentListOfNodes = tree.getNodeList();
            }
        }
        Swing a = new Swing(listTrees, listTreeNodes, session);

    }
}
