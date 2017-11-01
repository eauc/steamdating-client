(ns steamdating.components.player.edit-test
  (:require [devcards.core :as dc :refer-macros [defcard-rg]]
            [reagent.core :as reagent]
            [steamdating.components.player.edit :refer [player-edit-render]]
            [steamdating.models.form :as form]))


(defcard-rg player-edit-test.
  "Player edit component"


  (fn [state]
    [player-edit-render {:label "Player edit"
                         :on-submit #(println "submit")
                         :on-update-field #(do (println "update-field" %1 %2)
                                               (swap! state update :form form/assoc-field %1 %2))
                         :state (assoc (:form @state)
                                       :casters (get (:casters @state) (get-in @state [:form :edit :faction])))}])


  (reagent/atom
    {:form {:base {}
            :edit {}
            :factions {:Cygnar "Cygnar"
                       :Retribution "Retribution of Scyrah"
                       :Khador "Khador"
                       :Legion "Legion of Everblight"
                       :Trollbloods "Trollbloods"
                       :Minions "Minions"
                       :Protectorate "Protectorate of Menoth"
                       :Grymkin "Grymkin"
                       :Cryx "Cryx"
                       :Skorne "Skorne Empire"
                       :Mercenaries "Mercenaries"
                       :Convergence "Convergence of Cyriss"
                       :Circle "Circle Orboros"}
            :casters {}}
     :casters {"Khador" {:Malakov2 "Malakov2 (Kommander Andrei Malakov)"
                         :Harkevich1 "Harkevich1 (Kommander Harkevich, The Iron Wolf)"
                         :Irusk1 "Irusk1 (Kommandant Irusk)"
                         :Vlad3 "Vlad3 (Vladimir Tzepesci, Great Prince of Umbrey)"
                         :Irusk2 "Irusk2 (Supreme Kommandant Irusk)"
                         :Karchev1 "Karchev1 (Karchev the Terrible)"
                         :Butcher2 "Butcher2 (Kommander Orsus Zoktavir)"
                         :Strakhov1 "Strakhov1 (Kommander Strakhov)"
                         :TheOldWitch1 "TheOldWitch1 (Zevanna Agha, the Old Witch of Khador)"
                         :Sorscha1 "Sorscha1 (Kommander Sorscha)"
                         :Sorscha2 "Sorscha2 (Forward Kommander Sorscha)"
                         :Vlad1 "Vlad1 (Vladimir, The Dark Prince)"
                         :Zerkova1 "Zerkova1 (Koldun Kommander Aleksandra Zerkova)"
                         :Butcher3 "Butcher3 (Kommander Zoktavir, The Butcher Unleashed)"
                         :Butcher1 "Butcher1 (Orsus Zoktavir, The Butcher of Khardov)"
                         :Koslov1 "Koslov1 (Lord Koslov,Viscount of Sarsgrad)"
                         :Vlad2 "Vlad2 (Vladimir Tzepesci, the Dark Champion)"
                         :Zerkova2 "Zerkova2 (Obavnik Kommander Zerkova)"}
               "Legion" {:Kryssa1 "Kryssa1 (Kryssa, Conviction of Everblight)"
                         :Lylyth2 "Lylyth2 (Lylyth, Shadow of Everblight)"
                         :Saeryn&Rhyas2 "Saeryn&Rhyas2 (Saeryn & Rhyas, Talons of Everblight)"
                         :Absylonia2 "Absylonia2 (Absylonia, Daughter of Everblight)"
                         :Thagrosh1 "Thagrosh1 (Thagrosh, Prophet of Everblight)"
                         :Absylonia1 "Absylonia1 (Absylonia, Terror of Everblight)"
                         :Rhyas1 "Rhyas1 (Rhyas, Sigil of Everblight)"
                         :Bethayne1 "Bethayne1 (Bethayne & Belphagor)"
                         :Saeryn1 "Saeryn1 (Saeryn, Omen of Everblight)"
                         :Lylyth1 "Lylyth1 (Lylyth, Herald of Everblight)"
                         :Thagrosh2 "Thagrosh2 (Thagrosh, the Messiah)"
                         :Kallus1 "Kallus1 (Kallus, Wrath of Everblight)"
                         :Vayl1 "Vayl1 (Vayl, Disciple of Everblight)"
                         :Fyanna2 "Fyanna2 (Fyanna, Scourge of Everblight)"
                         :Lylyth3 "Lylyth3 (Lylyth, Reckoning of Everblight)"
                         :Vayl2 "Vayl2 (Vayl, Consul of Everblight)"}}})


  {:inspect-data true
   :history true})
