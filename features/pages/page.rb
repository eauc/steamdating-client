require "rspec/expectations"

module Pages
  class Page
    include RSpec::Matchers
    include Capybara::DSL

    def load
      visit "/#/#{@name}"
    end
  end
end
