package org.hibernate_test;

import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate_test.data.entities.NodePack;
import org.hibernate_test.data.util.HibernateSessionFactoryUtil;

import java.util.ArrayList;
import java.util.List;

public class Connection {
    private List<Tree> listTrees = new ArrayList<Tree>();
    private List<TreeNode> listTreeNodes = new ArrayList<TreeNode>();
    Session session = null;
    public void getConnection(int chooseDB) {

        switch (chooseDB) {
            case 1 -> {
                session = HibernateSessionFactoryUtil.getSessionFactoryPostgreSQL().openSession();
            }
            case 2 -> {
                session = HibernateSessionFactoryUtil.getSessionFactoryH2().openSession();
            }
        }
        session.beginTransaction();

        Query query = session.createQuery("from NodePack ");
        List<NodePack> list = query.getResultList();
        int[] idAndParentId = new int[2];
        for (NodePack nodePack : list) {
            idAndParentId[0] = nodePack.getId();
            idAndParentId[1] = nodePack.getParent_id();
            this.listTreeNodes.add(new TreeNode());
            listTreeNodes.get(listTreeNodes.size() - 1).setId(idAndParentId[0]);
            listTreeNodes.get(listTreeNodes.size() - 1).setParentNode(idAndParentId[1]);
            if (listTreeNodes.get(listTreeNodes.size() - 1).isRoot()) {
                listTrees.add(new Tree());
                listTrees.get(listTrees.size() - 1).setRoot(idAndParentId[0]);
            }
        }
        }
    public List<Tree> getListTrees(){
        return listTrees;
    }
    public List<TreeNode> getListTreeNodes(){
        return listTreeNodes;
    }
    public void addNode(NodePack nodePack){
        session.merge(nodePack);
    }
    public void commitChanges(){
        session.getTransaction().commit();
    }
    public void deleteNode(int id){
        session.createQuery("DELETE from NodePack where id = " + id).executeUpdate();
    }
}


