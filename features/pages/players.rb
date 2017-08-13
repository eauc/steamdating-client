require_relative "./page"

module Pages
  class Players < Page

    def initialize
      @route = "players"
    end

    def filter_with(filter)
      within(".sd-PageContent") do
        fill_in(placeholder: "Filter", with: filter)
      end
      sleep 0.5
      self
    end

    def sort_by(sort)
      within(".sd-PageContent") do
        find("th", text: "Name").click
        find("th", text: sort).click
      end
      self
    end

    def invert_sort_by(sort)
      within(".sd-PageContent") do
        find("th", text: sort).click
      end
      self
    end

    def start_create_player
      within(".sd-PageMenu") do
        click_on("Create Player")
      end
      within(".sd-PageContent") do
        expect(page).to have_content("Create player")
      end
      self
    end

    def fill_player_form(player)
      within(".sd-PageContent") do
        fill_in("Name", with: player["name"]) if player.key? "name"
        fill_in("Origin", with: player["origin"]) if player.key? "origin"
        select(player["faction"], from: "Faction") if player.key? "faction"
        player["lists"].each { |list| select(list, from: "Lists") } if player.key? "lists"
        fill_in("Notes", with: player["notes"]) if player.key? "notes"
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

    def start_edit_player(player)
      within(".sd-PageContent") do
        find("tr", text: player["name"]).click
        expect(page).to have_content("Edit player")
      end
      self
    end

    def edit_player(player, data)
      start_edit_player(player)
      fill_player_form(data)
      within(".sd-PageContent") do
        click_button({value: "submit"})
      end
      self
    end

    def delete_player(player)
      start_edit_player(player)
      within(".sd-PageMenu") do
        click_on("Delete")
      end
      self
    end

    def expect_player_in_list player
      values = [
        player["name"],
        player["origin"],
        player["faction"],
        player["lists"].sort,
      ]
      row_text = Regexp.new values.flatten.join(".*")
      within(".sd-PageContent") do
        expect(page).to have_selector("tr", text: row_text)
      end
      self
    end

    def expect_players_list(players)
      list = Regexp.new(players.map {|p| p.join(".*") }.join(".*"), "i")
      within(".sd-PageContent") do
        expect(page).to have_content(list)
      end
      self
    end

    def expect_players_list_with_headers(match)
      headers = match["headers"].join(".*")
      players = match["players"].map {|p| p.join(".*") }.join(".*")
      list = Regexp.new("#{headers}.*#{players}", "i")
      within(".sd-PageContent") do
        expect(page).to have_content(list)
      end
      self
    end
  end
end
