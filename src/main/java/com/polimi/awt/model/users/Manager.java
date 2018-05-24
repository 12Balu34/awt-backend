package com.polimi.awt.model.users;

import com.polimi.awt.model.Campaign;
import com.polimi.awt.model.CampaignStatus;
import com.polimi.awt.model.Role;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Manager extends User {

    public Manager() {
    }

    public Manager(String username, String password, String emailAddress, Role role) {
        super(username, password, emailAddress, role);
    }

    public Manager(String username, String password, String emailAddress) {
        super(username, password, emailAddress);
    }

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Set <Campaign> managedCampaigns = new HashSet<>();

    public Set<Campaign> getManagedCampaigns() {
        return managedCampaigns;
    }

    public void setManagedCampaigns(Set<Campaign> managedCampaigns) {
        this.managedCampaigns = managedCampaigns;
    }

    public Campaign createCampaign(String name){
        Campaign newCampaign = new Campaign();
        newCampaign.setName(name);
        newCampaign.setCampaignStatus(CampaignStatus.CREATED);
        managedCampaigns.add(newCampaign);
        return newCampaign;
    }
}
