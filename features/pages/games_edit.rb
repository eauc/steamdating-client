require_relative "./page"

module Pages
	class GamesEdit < Page
    def save_game
      within_fieldset("Edit game") do
        click_on("Save")
      end
      self
    end

		def fill_game_form(game_info)
			within_p1_name do
				page.select(game_info[:player1]) if game_info.key? :player1
			end
			within_table do
				page.fill_in(with: game_info[:table]) if game_info.key? :table
			end
			within_p2_name do
				page.select(game_info[:player2]) if game_info.key? :player2
			end
			within_p1_list do
				page.select(game_info[:p1list]) if game_info.key? :p1list
			end
			within_p2_list do
				page.select(game_info[:p2list]) if game_info.key? :p2list
			end
			within_p1_ck do
				set_assassination(game_info[:p1ck])
			end
			within_p2_ck do
				set_assassination(game_info[:p2ck])
			end
			within_p1_cp do
				page.fill_in(with: game_info[:p1cp]) if game_info.key? :p1cp
			end
			within_p2_cp do
				page.fill_in(with: game_info[:p2cp]) if game_info.key? :p2cp
			end
			within_p1_ap do
				page.fill_in(with: game_info[:p1ap]) if game_info.key? :p1ap
			end
			within_p2_ap do
				page.fill_in(with: game_info[:p2ap]) if game_info.key? :p2ap
			end
			sleep 0.5
      self
		end

		def set_assassination(is_assa)
			case is_assa
			when "true"
				page.check("assassination")
			when "false"
				page.uncheck("assassination")
			end
      self
		end

		def expect_game_form(game_info)
			# puts game_info
			within_p1_name do
				expect(page).to have_select(selected: game_info[:player1]) if game_info.key? :player1
			end
			within_table do
				expect(page).to have_field(with: game_info[:table]) if game_info.key? :table
			end
			within_p2_name do
				expect(page).to have_select(selected: game_info[:player2]) if game_info.key? :player2
			end
			within_p1_list do
				expect(page).to have_select(selected: game_info[:p1list]) if game_info.key? :p1list
			end
			within_p2_list do
				expect(page).to have_select(selected: game_info[:p2list]) if game_info.key? :p2list
			end
			within_win_loss do
				expected_content = [
					win_loss_label(game_info[:p1wl]),
					win_loss_label(game_info[:p2wl]),
				].join("\\s+")
				# puts page.text
				expect(page).to have_content(Regexp.new("^#{expected_content}$", "i"))
			end
			within_p1_ck do
        expect_assassination(game_info[:p1ck])
			end
			within_p2_ck do
        expect_assassination(game_info[:p2ck])
			end
			within_p1_cp do
				expect(page).to have_field(with: game_info[:p1cp]) if game_info.key? :p1cp
      end
			within_p2_cp do
				expect(page).to have_field(with: game_info[:p2cp]) if game_info.key? :p2cp
      end
			within_p1_ap do
				expect(page).to have_field(with: game_info[:p1ap]) if game_info.key? :p1ap
      end
			within_p2_ap do
				expect(page).to have_field(with: game_info[:p2ap]) if game_info.key? :p2ap
      end
      self
		end

		def expect_assassination(is_assa)
			case is_assa
			when "true"
				expect(page).to have_checked_field("assassination")
			when "false"
				expect(page).to have_unchecked_field("assassination")
			end
      self
		end

		def win_loss_label(win_loss)
			case win_loss
			when "W"
				"Winner\\s+\\(Click to set Draw\\)"
			when "L"
				"Looser\\s+\\(Click to set Winner\\)"
			else
				"Click to set Winner"
			end
		end

		def within_players
			within(PAGE_CONTENT) do
				within("tbody") do
					within(:xpath, "(.//tr)[1]") do
						yield
					end
				end
			end
      self
		end

		def within_p1_name
			within_players do
				within(:xpath, "(.//td|.//th)[1]") do
					yield
				end
			end
      self
		end

		def within_table
			within_players do
				within(:xpath, "(.//td|.//th)[2]") do
					yield
				end
			end
      self
		end

		def within_p2_name
			within_players do
				within(:xpath, "(.//td|.//th)[3]") do
					yield
				end
			end
      self
		end

		def within_lists
			within(PAGE_CONTENT) do
				within("tbody") do
					within(:xpath, "(.//tr)[2]") do
						yield
					end
				end
			end
      self
		end

		def within_p1_list
			within_lists do
				within(:xpath, "(.//td|.//th)[1]") do
					yield
				end
			end
      self
		end

		def within_p2_list
			within_lists do
				within(:xpath, "(.//td|.//th)[3]") do
					yield
				end
			end
      self
		end

		def within_win_loss
			within(PAGE_CONTENT) do
				within("tbody") do
					within(:xpath, "(.//tr)[3]") do
						yield
					end
				end
			end
      self
		end

		def within_ck
			within(PAGE_CONTENT) do
				within("tbody") do
					within(:xpath, "(.//tr)[4]") do
						yield
					end
				end
			end
      self
		end

		def within_p1_ck
			within_ck do
				within(:xpath, "(.//td|.//th)[1]") do
					yield
				end
			end
      self
		end

		def within_p2_ck
			within_ck do
				within(:xpath, "(.//td|.//th)[3]") do
					yield
				end
			end
      self
		end

		def within_cp
			within(PAGE_CONTENT) do
				within("tbody") do
					within(:xpath, "(.//tr)[5]") do
						yield
					end
				end
			end
      self
		end

		def within_p1_cp
			within_cp do
				within(:xpath, "(.//td|.//th)[1]") do
					yield
				end
			end
      self
		end

		def within_p2_cp
			within_cp do
				within(:xpath, "(.//td|.//th)[3]") do
					yield
				end
			end
      self
		end

		def within_ap
			within(PAGE_CONTENT) do
				within("tbody") do
					within(:xpath, "(.//tr)[6]") do
						yield
					end
				end
			end
      self
		end

		def within_p1_ap
			within_ap do
				within(:xpath, "(.//td|.//th)[1]") do
					yield
				end
			end
      self
		end

		def within_p2_ap
			within_ap do
				within(:xpath, "(.//td|.//th)[3]") do
					yield
				end
			end
      self
		end
	end
end
