require "rspec/expectations"

module Pages
  class Page
    include RSpec::Matchers
    include Capybara::DSL

    def load
      visit "/#/#{@route}"
    end
  end
end
