require_relative "./page"

module Pages
	class Follow < Page
    def filter_with(filter)
      within(PAGE_CONTENT) do
        fill_in(placeholder: "Filter", with: filter)
      end
      sleep 0.5
      self
    end

		def expect_ranking_list(ranking)
			within_table("Ranking") do
				within("tbody") do
					expected_content = ranking.map {|r|
						r.reject{|c| c.is_a?(String) and c.empty?}.join("\\s+")
					}.join("\\s+")
					expect(page).to have_content(Regexp.new("^\\s*#{expected_content}\\s*$", "i"))
				end
			end
			self
		end

    def expect_round(nth, games)
      expected_content = games.map do |game|
        [
          game[:p1ap] || 0,
          game[:p1cp] || 0,
          game[:player1] || "Phantom",
          game[:p1list] || "",
          game[:table] || 1,
          game[:player2] || "Phantom",
          game[:p2list] || "",
          game[:p2cp] || 0,
          game[:p2ap] || 0,
        ].reject { |c| c.is_a?(String) and c.empty? }
          .join("\\s+")
      end.join("\\s+")

      within_table("Round ##{nth}") do
        within("tbody") do
          expect(page).to have_content(Regexp.new("^\\s*#{expected_content}\\s*$", "i"))
        end
      end
      self
    end

    def expect_players_list(players)
      list = players.map {|p| p.join("\\s+") }.join("\\s+")
      within_table("Players") do
        within("tbody") do
          expect(page).to have_content(Regexp.new("^\\s*#{list}\\s*$", "i"))
        end
      end
      self
    end
	end
end
