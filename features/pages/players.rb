require_relative "./page"

module Pages
  class Players < Page

    def initialize
      @route = "players"
    end

    def start_create_player
      within(".sd-PageMenu") do
        click_on("Create Player")
      end
      self
    end

    def fill_player_form(player)
      within(".sd-PageContent") do
        fill_in("Name", with: player[:name]) if player.key? :name
        fill_in("Origin", with: player[:origin]) if player.key? :origin
        fill_in("Notes", with: player[:notes]) if player.key? :notes
        select(player[:faction], from: "Faction") if player.key? :faction
        player[:lists].each { |list| select(list, from: "Lists") } if player.key? :lists
      end
      sleep 0.5
      self
    end

    def create_player(player)
      start_create_player
      fill_player_form(player)
      within(".sd-PageContent") do
        click_button({value: "submit"})
      end
      self
    end

    def expect_player_in_list player
      values = [
        player[:name],
        player[:origin],
        player[:faction],
        player[:lists].sort,
      ]
      row_text = Regexp.new values.flatten.join(".*")
      within(".sd-PageContent") do
        expect(page).to have_selector("tr", text: row_text)
      end
      self
    end
  end
end
