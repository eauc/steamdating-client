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
			expected_content = ranking.map {|r|
				r.reject{|c| c.is_a?(String) and c.empty?}.join("\\s+")
			}.join("\\s+")

			within(:table, "Rankings", match: :first) do
				within("tbody") do
					# puts page.text
					# puts expected_content
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

			within(:table, "Round ##{nth}", match: :first) do
				within("tbody") do
					expect(page).to have_content(Regexp.new("^\\s*#{expected_content}\\s*$", "i"))
				end
			end
			self
		end

		def expect_rounds_summary(rows)
      expected_content = rows
                           .map {|row| row.reject {|c| c.empty?}.join("\\s+")}
                           .join("\\s+")

      within(:table, "Rounds", match: :first) do
        within("tbody") do
          # puts page.text
          # puts expected_content
          expect(page).to have_content(Regexp.new("^\\s*#{expected_content}\\s*$", "i"))
        end
      end
			self
		end

		def expect_players_list(players)
			list = players.map {|p| p.join("\\s+") }.join("\\s+")
			within(:table, "Players", match: :first) do
				within("tbody") do
					# puts page.text
					# puts list
					expect(page).to have_content(Regexp.new("^\\s*#{list}\\s*$", "i"))
				end
			end
			self
		end
	end
end
